package com.example.myobjectserver.Myfilter;


import com.example.myobjectserver.exception.ErrorResponse;
import com.example.myobjectserver.result.ResultCodeEnum;
import com.example.myobjectserver.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 恒光
 * createTime:2025-04-01
 * version:1.0
 */
public class UserFilter extends OncePerRequestFilter {
    //精准匹配接口
    List<String> bypassPaths = List.of(
            "/api/config/wallpaperList", //主页config壁纸配置
            "/api/engine/list", //引擎列表
            "/api/users/login", //登录
            "/api/users/register",
            "/api/resource/getOne", // 获取单个资源
            "/api/config/getLoginWallpaperConfig", // 获取登录壁纸config配置
            "/api/config/getRegisterWallpaperConfig",//获取注册页面配置
            "/api/users/parseUserToken" //解析token返回type"
            );
    //参数不同接口
    List<String> bypassPathsTwo = List.of(
            "/api/classify/getClassifyListById/", // 根据目录id获取所有资源
            "/api/resource/getOne/", //根据资源id获取资源
            "/api/users/checkUsername/",
            "/images/" //静态图片
    );


    //认证完成之后普通用户可访问
    List<String> tokenPaths = List.of(
            "/api/classify/classifyList", //获取所有目录
            "/api/resource/list", // 获取所有资源
            "/api/users/router/getRouterList",//获取路由列表
            "/api/users/getUserInfo",//获取用户信息,
            "/api/classify/getStatusList", // 获取设置分类列表
            "/api/resource/byList" //获取设置图片列表
    );
    //认证完成之后普通用户可访问
    List<String> tokenPathsZ = List.of(
            "/api/users/routers/" //路由列表
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(bypassPaths.contains(path) || bypassPathsTwo.stream().anyMatch(path::startsWith)){
            filterChain.doFilter(request,response);
            return;
        }

       /* TestingAuthenticationToken token = new TestingAuthenticationToken("username", "password", "ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(token);*/
        String authorization = request.getHeader("Authorization");
        if(authorization != null){
            try{
                Claims claims = JwtUtil.parsePayload(authorization);
                String username = claims.getSubject();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println(path);
                String type = (String) claims.get("type");

                if(tokenPaths.contains(path) || tokenPathsZ.stream().anyMatch(path::startsWith)){
                    filterChain.doFilter(request,response);
                    return;
                }

                //1 == admin 2 == user
                if("1".equals(type)){
                    filterChain.doFilter(request,response);
                    return;
                }
                //System.out.println(path);
                writer(response,ResultCodeEnum.NO_AUTH);
            }catch (Exception ex){
                //令牌过期
               System.out.println(ex.getClass());
               writer(response,ResultCodeEnum.EXPIRED_JWT);

            }
        }else {
            filterChain.doFilter(request,response);
        }
    }


    private void writer(HttpServletResponse response,ResultCodeEnum resultCodeEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new ErrorResponse(resultCodeEnum));
        // 写入响应
        response.getWriter().write(jsonResponse);
    }

}

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
@Slf4j
public class UserFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        log.info("path: {}", path);


       /* TestingAuthenticationToken token = new TestingAuthenticationToken("username", "password", "ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(token);*/
        String authorization = request.getHeader("Authorization");
        if(authorization != null){
            try{
                Claims claims = JwtUtil.parsePayload(authorization);
                String username = claims.getSubject();
                List<String> roles = (List<String>) claims.get("role");
                List<GrantedAuthority> authorities = new ArrayList<>();
                for(String role : roles){
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("用户已登录:放行了{}",path);
                filterChain.doFilter(request,response);
            }catch (Exception ex){
                //令牌过期
               log.error("令牌过期{}",ex.getMessage());
               writer(response,ResultCodeEnum.EXPIRED_JWT);

            }
        }else {
            log.info("用户未登录:放行了{}",path);
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

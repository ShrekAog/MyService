package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.UsersMapper;
import com.example.myobjectserver.mapper.UsersRouterMapper;
import com.example.myobjectserver.pojo.Users;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.services.UsersService;
import com.example.myobjectserver.utils.JwtUtil;
import com.example.myobjectserver.vo.UserVo;
import com.example.myobjectserver.vo.UsersRoutersVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService, UserDetailsService {


    private final UsersMapper usersMapper;
    private final UsersRouterMapper routerMapper;
    private AuthenticationManager manager;
    private final PasswordEncoder passwordEncoder;

    /**
     * 依赖构造注入
     * @param usersMapper 用户Mapper
     * @param routerMapper 路由Mapper
     */
    public UsersServiceImpl(UsersMapper usersMapper, UsersRouterMapper routerMapper, PasswordEncoder passwordEncoder) {
        this.usersMapper = usersMapper;
        this.routerMapper = routerMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired
    @Lazy
    public void setAuthenticationManager(AuthenticationManager manager){
        this.manager = manager;
    }


    /**
     * 根据用户类型返回对应的路由表
     *
     * @param userType 用户类型
     * @return 用户路由列表
     */
    @Override
    public List<UsersRouter> getUserRoutersById(Integer userType) {
        return usersMapper.getUserRoutersById(userType);
    }

    /**
     *根据路由对象修改数据
     * @param router 路由对象
     * @return 修改成功后的数量
     */
    @Override
    public Integer updateRouters(UsersRouter router){
        return routerMapper.updateById(router);
    }


    /**
     * 自定义登录
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 找不到用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users users = usersMapper.selectOne(wrapper);
        if(users == null){
            throw new UsernameNotFoundException("User found::" + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(users.getUserType() == 1){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        users.setAuthorities(authorities);
        return users;
    }

    @Override
    public Users login(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        Users userDetails = (Users) authenticate.getPrincipal();

        String token1 = JwtUtil.createToken(username, String.valueOf(userDetails.getUserType()));
        userDetails.setToken(token1);
        log.info("用户 {} 登录成功",username);
        log.info("用户权限 {} ",userDetails.getAuthorities().toString());

        return userDetails;
    }

    @Override
    public Boolean register(String username, String password) {
        if(username == null || password == null){return false;}
        Users users = new Users();

        users.setUsername(username);
        users.setPassword(passwordEncoder.encode(password));
        int insert = usersMapper.insert(users);
        return insert == 1;
    }

    @Override
    public UserVo getUserInfoByUsername(String username) {
        Users users = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
        return UserVo.builder()
                .id(users.getId())
                .username(users.getUsername())
                .nickName(users.getNickName())
                .avatar(users.getAvatar())
                .email(users.getEmail())
                .phoneNumber(users.getPhoneNumber())
                .gender(users.getGender())
                .introduction(users.getIntroduction())
                .userType(users.getUserType())
                .build();
    }

    @Override
    public Boolean checkUsernameExist(String username) {
        Users users = usersMapper.checkUsernameExist(username);
        return users != null;
    }

    @Override
    public List<UsersRoutersVo> getUserRoutersVoByList(List<UsersRouter> routers) {
        List<UsersRoutersVo> result = new ArrayList<>();
        // 先处理父级菜单
        for (UsersRouter item : routers) {
            // 只处理父级菜单
            if (item.getParentId() == 0) {
                UsersRoutersVo parentVo = UsersRoutersVo.builder()
                        .id(item.getId())
                        .text(item.getText())
                        .path(item.getPath())
                        .iconPath(item.getIconPath())
                        .parentId(Integer.valueOf(item.getParentId()))
                        .children(new ArrayList<>())
                        .build();
                result.add(parentVo);
            }
        }

        // 再处理子菜单
        for (UsersRouter item : routers) {
            // 只处理子菜单
            if (item.getParentId() != 0) {
                // 找到对应的父菜单
                for (UsersRoutersVo parentVo : result) {
                    if (parentVo.getId() == Integer.valueOf(item.getParentId())) {
                        UsersRoutersVo childVo = UsersRoutersVo.builder()
                                .id(item.getId())
                                .text(item.getText())
                                .path(item.getPath())
                                .iconPath(item.getIconPath())
                                .parentId(Integer.valueOf(item.getParentId()))
                                .build();
                        parentVo.getChildren().add(childVo);
                        break;
                    }
                }
            }
        }
        return result;
    }
}

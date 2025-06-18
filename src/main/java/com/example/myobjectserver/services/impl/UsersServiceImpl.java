package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.UsersMapper;
import com.example.myobjectserver.pojo.Users;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.services.UsersService;
import com.example.myobjectserver.utils.JwtUtil;
import com.example.myobjectserver.vo.UserVo;
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
    private AuthenticationManager manager;
    private final PasswordEncoder passwordEncoder;

    /**
     * 依赖构造注入
     * @param usersMapper 用户Mapper
     *
     */
    public UsersServiceImpl(UsersMapper usersMapper, PasswordEncoder passwordEncoder) {
        this.usersMapper = usersMapper;
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
     *//*
    @Override
    public List<UsersRouter> getUserRoutersById(Integer userType) {
        return usersMapper.getUserRoutersById(userType);
    }*/
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
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
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
        Users userDetails = (Users) authenticate.getPrincipal();
      //  SecurityContextHolder.getContext().setAuthentication(authenticate);

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        String token1 = JwtUtil.createToken(username,roles);
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
    public UserVo getUserInfoById(Integer id) {
        Users users = usersMapper.selectById(id);
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
        Users users = usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
        return users != null;
    }

    @Override
    public Boolean checkJwtIsExpired(String token) {
        return JwtUtil.isTokenExpired(token);
    }


}

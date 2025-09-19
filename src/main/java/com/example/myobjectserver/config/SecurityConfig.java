
package com.example.myobjectserver.config;


import com.example.myobjectserver.Myfilter.UserFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author 恒光
 * createTime:2025-04-01
 * version:1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${my.security.adminPath}")
    private String adminPath;
    @Value("${my.security.userPath}")
    private String userPath;
    @Value("${my.security.publicPath}")
    private String publicPath;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable);


        String[] adminPaths = Arrays.stream(adminPath.split(",")).map(String::trim).toArray(String[]::new);
        String[] publicPaths = Arrays.stream(publicPath.split(",")).map(String::trim).toArray(String[]::new);
        String[] userPaths = Arrays.stream(userPath.split(",")).map(String::trim).toArray(String[]::new);
        http.authorizeHttpRequests(authorization -> authorization
                .requestMatchers(publicPaths).permitAll()
                .requestMatchers(userPaths).hasAnyRole("USER", "ADMIN")
                .requestMatchers(adminPaths).hasRole("ADMIN")

                //检查用户名是否存在
                .requestMatchers("/api/users/checkUsername").permitAll()
                //验证token是否过期
                .requestMatchers("/api/users/checkToken/**").permitAll()
                //获取主页壁纸配置文件
                .requestMatchers("/api/config/wallpaperList").permitAll()
                //获取引擎列表
                //.requestMatchers("/api/engine/list").hasRole("ADMIN")
                //根据目录ID获取资源列表
                .requestMatchers("/api/classify/getClassifyListById/**").permitAll()
                //获取登录页壁纸配置文件
                .requestMatchers("/api/config/getLoginWallpaperConfig").permitAll()
                //获取注册页壁纸配置文件
                .requestMatchers("/api/config/getRegisterWallpaperConfig").permitAll()
                //根据id获取单个资源
                .requestMatchers("/api/resource/getOne/**").permitAll()
                //获取所有资源
                .requestMatchers("/api/resource/byList").permitAll()
                //根据用户id获取用户信息
                .requestMatchers("/api/users/getUserInfo/**").permitAll()
                //根据用户id获取路由列表
                .requestMatchers("/api/users/routers/**").permitAll()

                .requestMatchers("/api/note/add").permitAll()
                .requestMatchers("/api/note/list").permitAll()
                .requestMatchers("/api/note/add").permitAll()

                //放行服务器资源

                .anyRequest().authenticated());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.addFilterBefore(new UserFilter(), UsernamePasswordAuthenticationFilter.class);

        //配置认证管理器
        http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        //许允跨域的域名
        configuration.addAllowedOriginPattern("*");
        //允许的请求方式
        configuration.addAllowedMethod("*");
        //允许设置header
        configuration.addAllowedHeader("*");
        //cookie
        configuration.setAllowCredentials(true);
        //最大请求时间
        configuration.setMaxAge(10000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //允许跨域的路径
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}

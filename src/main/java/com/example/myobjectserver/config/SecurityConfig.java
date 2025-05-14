
package com.example.myobjectserver.config;


import com.example.myobjectserver.Myfilter.UserFilter;
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

/**
 * @author 恒光
 * createTime:2025-04-01
 * version:1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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




        http.authorizeHttpRequests(authorization -> authorization
                .requestMatchers("/api/users/login").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/users/checkUsername/**").permitAll()
                .requestMatchers("/api/config/wallpaperList").permitAll()
                .requestMatchers("/api/engine/list").permitAll()
                .requestMatchers("/api/classify/getClassifyListById/**").permitAll()
                .requestMatchers("/api/config/getLoginWallpaperConfig").permitAll()
                .requestMatchers("/api/config/getRegisterWallpaperConfig").permitAll()
                .requestMatchers("/api/resource/getOne/**").permitAll()
                .requestMatchers("/api/users/parseUserToken").permitAll()
                .requestMatchers("/images/**").permitAll()
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

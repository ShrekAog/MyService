package com.example.myobjectserver.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 恒光
 * createTime:2025-04-01
 * version:1.0
 */
@Component
public class JwtUtil {
    @Value("${my.jwt.key}")
    private String key;

    @Value("${my.jwt.time}")
    private Long time;

    @Value("${my.jwt.iss}")
    private String iss;

    private static String key1;
    private static Long time1;
    private static String iss1;
    private static SecretKey KEYS;

    @PostConstruct
    private void init(){
        JwtUtil.key1 = key;
        JwtUtil.time1 = time;
        JwtUtil.iss1 = iss;
        KEYS = Keys.hmacShaKeyFor(key1.getBytes());
    }

    /**
     * 创建token
     * @return token
     */
    public static String createToken(String username,Integer userId, List<String> role){
        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .add("alg","HS256")
                .and()

                .claim("good","good job!")
                .claim("role",role)
                .claim("userId",userId)
                .id(UUID.randomUUID().toString())
                .expiration(new Date(System.currentTimeMillis() + time1 * 1000))
                .issuedAt(new Date(System.currentTimeMillis()))
                .subject(username)
                .issuer(iss1)
                .signWith(KEYS,Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 根据token进行解析
     * @param token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token){
        return Jwts.parser()
                .verifyWith(KEYS)
                .build()
                .parseSignedClaims(token);
    }

    /**
     * 根据token获取头信息
     * @param token
     * @return header
     */
    public static JwsHeader parseHeader(String token){
        return parseClaim(token).getHeader();
    }

    /**
     * 根据token获取主体
     * @param token
     * @return claims
     */
    public static Claims parsePayload(String token){
        return parseClaim(token).getPayload();
    }

    /**
     * 根据token获取预设的过期时间
     * @param token
     * @return date
     */
    public static Date getExpireDateFormToken(String token){
        return parseClaim(token).getPayload().getExpiration();
    }

    /**
     * 判断token是否过期
     * @param token token
     * @return true过期 false没过期
     */
    public static boolean isTokenExpired(String token){
        try {
            return getExpireDateFormToken(token).before(new Date());
        }catch (ExpiredJwtException ex){
            return true;
        }
    }

}

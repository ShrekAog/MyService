package com.example.myobjectserver.utils;

import com.example.myobjectserver.dto.UserDetailsInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 恒光
 * createTime:2025-09-08
 * version:1.0
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户的详细信息
     * @return UserDetailsInfo，若未登录返回 null
     */
    public static UserDetailsInfo getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getDetails() == null) {
            return null;
        }
        return (UserDetailsInfo) authentication.getDetails();
    }
}

package com.example.myobjectserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 恒光
 * createTime:2025-09-08
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsInfo {
    private String username;
    private Integer userId;
}

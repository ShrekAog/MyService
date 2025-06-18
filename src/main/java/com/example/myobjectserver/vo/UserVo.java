package com.example.myobjectserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*@author 恒光
*createTime:2025-04-07
*version:1.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {
    private Integer id;
    private String username;
    private String nickName;
    private String avatar;
    private String email;
    private String introduction;
    private Integer gender;
    private Integer userType;
    private String phoneNumber;
    private String token;
}

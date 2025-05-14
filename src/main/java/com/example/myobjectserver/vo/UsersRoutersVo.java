package com.example.myobjectserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-30
 * version:1.0
 */
@Data
@AllArgsConstructor
@Builder
public class UsersRoutersVo {
    private Integer id;
    private String path;
    private String iconPath;
    private String text;
    private Integer parentId;
    private List<UsersRoutersVo> children;
}

package com.example.myobjectserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 恒光
 * createTime:2025-06-18
 * version:1.0
 */
@Data
@AllArgsConstructor
@Builder
public class RouterVo {
    private int id;
    private String text;
    private String path;
    private String iconPath;
    private Integer parentId;
    private List<RouterVo> children;

}

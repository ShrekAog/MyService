package com.example.myobjectserver.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 恒光
 * createTime:2025-09-05
 * version:1.0
 */
@Data
public class ResourceDTOWrapper {
    private Integer userId;
    private Integer classify;
    private String type;
    private List<ResourceDTO> resource;
}

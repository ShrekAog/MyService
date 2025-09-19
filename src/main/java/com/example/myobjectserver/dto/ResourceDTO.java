package com.example.myobjectserver.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 恒光
 * createTime:2025-09-05
 * version:1.0
 */
@Data
public class ResourceDTO {
    private String uid;
    private String name;
    private MultipartFile file;
}

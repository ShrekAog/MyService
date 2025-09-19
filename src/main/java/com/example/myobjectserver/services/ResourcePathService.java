package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.pojo.ResourcePath;

import java.util.List;

/**
 * @author 恒光
 * createTime:2025-03-20
 * version:1.0
 */
public interface ResourcePathService extends IService<ResourcePath> {
    /**
     * 根据目录ID获取资源列表
     * @param id 目录id
     * @return 资源列表
     */
    List<Resource> getListById(Integer id);

    Boolean removePathById(Integer id);
}

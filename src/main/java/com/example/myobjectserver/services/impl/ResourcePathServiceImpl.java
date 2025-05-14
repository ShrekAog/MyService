package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.ResourceMapper;
import com.example.myobjectserver.mapper.ResourcePathMapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.pojo.ResourcePath;
import com.example.myobjectserver.services.ResourcePathService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 恒光
 * createTime:2025-03-20
 * version:1.0
 */
@Service
public class ResourcePathServiceImpl extends ServiceImpl<ResourcePathMapper, ResourcePath> implements ResourcePathService {
    
    private final ResourceMapper resourceMapper;
    private final ResourcePathMapper pathMapper;
    public ResourcePathServiceImpl(ResourceMapper resourceMapper,ResourcePathMapper pathMapper){
        this.resourceMapper = resourceMapper;
        this.pathMapper = pathMapper;
    }
    
    
    
    @Override
    public List<Resource> getListById(Integer id) {
        QueryWrapper<ResourcePath> pathQueryWrapper = new QueryWrapper<>();
        pathQueryWrapper.eq("id",id);
        pathQueryWrapper.eq("status",1);
        ResourcePath resourcePath = pathMapper.selectOne(pathQueryWrapper);
        if(resourcePath != null){
            QueryWrapper<Resource> wrapper = new QueryWrapper<>();
            wrapper.eq("resource_path_id",resourcePath.getId());
            wrapper.eq("status",1);
            return resourceMapper.selectList(wrapper);
        }
        return null;
    }
}

package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.dto.UserDetailsInfo;
import com.example.myobjectserver.exception.MyRunTimeException;
import com.example.myobjectserver.mapper.ResourceMapper;
import com.example.myobjectserver.mapper.ResourcePathMapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.pojo.ResourcePath;
import com.example.myobjectserver.result.ResultCodeEnum;
import com.example.myobjectserver.services.ResourcePathService;
import com.example.myobjectserver.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author 恒光
 * createTime:2025-03-20
 * version:1.0
 */
@Service
@Slf4j
public class ResourcePathServiceImpl extends ServiceImpl<ResourcePathMapper, ResourcePath> implements ResourcePathService {
    @Value("${my.upload.file-upload-path}")
    private String uploadDir;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removePathById(Integer id) {
        UserDetailsInfo details = SecurityUtil.getCurrentUserDetails();
        ResourcePath path = pathMapper.selectById(id);
        List<Resource> list = resourceMapper.selectList(new QueryWrapper<Resource>().eq("resource_path_id", path.getId()));
        for(Resource resource : list){
            String fileName = resource.getPath().substring(resource.getPath().lastIndexOf("/") + 1);
            String filePath = null;
            if(details != null){
                filePath = System.getProperty("user.dir") + uploadDir + "user" + details.getUserId() + "/" + fileName;
                File file = new File(filePath);
                if(file.exists() && file.delete()){
                    resourceMapper.deleteById(resource.getId());
                }else {
                    throw new MyRunTimeException(ResultCodeEnum.FILE_UNKNOWN);
                }
            }
        }

        return pathMapper.deleteById(id) >= 1;
    }
}

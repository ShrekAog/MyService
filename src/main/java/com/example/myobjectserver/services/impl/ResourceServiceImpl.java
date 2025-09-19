package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.dto.ResourceDTO;
import com.example.myobjectserver.dto.ResourceDTOWrapper;
import com.example.myobjectserver.dto.UserDetailsInfo;
import com.example.myobjectserver.exception.MyRunTimeException;
import com.example.myobjectserver.mapper.ResourceMapper;
import com.example.myobjectserver.mapper.ResourcePathMapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.pojo.ResourcePath;
import com.example.myobjectserver.result.ResultCodeEnum;
import com.example.myobjectserver.services.ResourceService;
import com.example.myobjectserver.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author KORE
 */
@Service
@Slf4j
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Value("${my.upload.file-upload-path}")
    private String uploadDir;
    private final ResourceMapper resourceMapper;
    public ResourceServiceImpl(ResourceMapper resource, ResourcePathMapper resourcePathMapper){
        this.resourceMapper = resource;
    }

    @Override
    public Integer getResourceCount(Integer classifyId) {
        return resourceMapper.selectCount(
                new LambdaQueryWrapper<Resource>()
//                        .notLike(Resource::getPath,".mp4")
                        .eq(Resource::getResourcePathId,classifyId)
        ).intValue();
    }

    @Override
    public Map<String, Object> getResourceList(Integer current, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.ne("type","icon");

        Page<Resource> page = new Page<>(current, pageSize);
        Page<Resource> paged = resourceMapper.selectPage(page,wrapper);
        map.put("current",paged.getCurrent());
        map.put("total",paged.getTotal());
        map.put("list",paged.getRecords());
        return map;
    }

    @Override
    public Map<String,Object> getResourceListByClassifyId(Integer classifyId,Integer current, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_path_id",classifyId);
        wrapper.orderByDesc("create_time");
        wrapper.orderByDesc("id");
//        wrapper.notLike("path",".mp4");
        Page<Resource> page = new Page<>(current, pageSize);
        Page<Resource> paged = resourceMapper.selectPage(page,wrapper);
        map.put("current",paged.getCurrent());
        map.put("total",paged.getTotal());
        map.put("list",paged.getRecords());
        return map;
    }

    @Override
    public List<Resource> getResourceVideoListByUserId(Integer userId) {
        return resourceMapper.selectList(
                new LambdaQueryWrapper<Resource>()
                        .eq(Resource::getUserId,userId)
                        .like(Resource::getMimeType,"video/")
        );
    }

    /**
     * 上传文件
     * @param file 上传文件
     * @param type 文件类型
     * @return 1 || 0
     */
    @Override
    public Integer uploadResource(MultipartFile file,String type,Integer classify) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePrefix = fileName.substring(0,fileName.indexOf("."));
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //String fileType = file.getContentType().substring(0,file.getContentType().indexOf("/"));
        String fileUuid = UUID.randomUUID() + "." + fileSuffix;
        int size = (int) file.getSize();

        String mimeType = file.getContentType();
        String host = "https://api.konosubaz.cn:49327" + "/images/" + fileUuid;



        file.transferTo(new File(System.getProperty("user.dir") + uploadDir,fileUuid));
        Resource fileBuilder = Resource.builder()
                .id(null)
                .userId(1)
                .type(type)
                .path(host)
                .size(size)
                .originalName(filePrefix)
                .mimeType(mimeType)
                .status(true)
                .storeType("local")
                .resourcePathId(classify)
                .createTime(null)
                .build();

       resourceMapper.insert(fileBuilder);
       return fileBuilder.getId();
    }



    @Override
    public List<Resource> uploadResource(ResourceDTOWrapper wrapper) throws IOException {
        ArrayList<Resource> list = new ArrayList<>();
        List<ResourceDTO> resource = wrapper.getResource();
        Integer userId = wrapper.getUserId();
        for(ResourceDTO resourceDTO : resource){
            MultipartFile file = resourceDTO.getFile();

            String filePrefix = resourceDTO.getName().substring(0,resourceDTO.getName().lastIndexOf("."));
            String fileSuffix = resourceDTO.getName().substring(resourceDTO.getName().lastIndexOf("."));
            System.out.println(filePrefix);
            System.out.println(fileSuffix);
            String fileUuid = UUID.randomUUID() + fileSuffix;
            int size = (int) file.getSize();
            String mimeType = file.getContentType();
            String host = "http://192.168.1.220:8080"+ uploadDir + "user" + userId + "/" + fileUuid;
            file.transferTo(new File(System.getProperty("user.dir") + uploadDir + "/" + "user" + userId, fileUuid));
            Resource build = Resource.builder()
                    .id(null)
                    .userId(userId)
                    .type(wrapper.getType())
                    .path(host)
                    .size(size)
                    .originalName(filePrefix)
                    .mimeType(mimeType)
                    .status(true)
                    .storeType("local")
                    .resourcePathId(wrapper.getClassify())
                    .createTime(null)
                    .build();
            resourceMapper.insert(build);
            list.add(build);
        }
        return list;
    }

    /**
     * 删除单个资源
     * @param id 资源id
     * @return true || false
     */
    @Override
    public Integer deleteResourceById(Integer id,Integer userId) {
        Resource resource = resourceMapper.selectById(id);
        String fileName = resource.getPath().substring(resource.getPath().lastIndexOf("/") + 1);
        String filePath = System.getProperty("user.dir") + uploadDir + "user" + userId  + "/" + fileName;
        File file = new File(filePath);
        if(file.exists() && file.delete()){
            log.info("资源文件删除成功");
            return resourceMapper.deleteById(id) == 1 ? id : -1;
        } else {
            throw new MyRunTimeException(ResultCodeEnum.FILE_UNKNOWN);
        }
    }

    /**
     * 删除多个资源
     * @param ids 数组id
     * @return true || false
     */
    @Override
    public Boolean deleteResourceByIdList(List<Integer> ids) {
        UserDetailsInfo details = SecurityUtil.getCurrentUserDetails();
        try {
            for (Resource resource : resourceMapper.selectByIds(ids)) {
                String fileName = resource.getPath().substring(resource.getPath().lastIndexOf("/") + 1);
                String filePath = null;
                if (details != null) {
                    filePath = System.getProperty("user.dir") + uploadDir + "user" + details.getUserId() + "/" + fileName;
                    File file = new File(filePath);
                    if(file.exists() && file.delete()){
                        resourceMapper.deleteById(resource.getId());
                    } else {
                        throw new MyRunTimeException(ResultCodeEnum.FILE_UNKNOWN);
                    }
                }
            }
            return true;
        }catch (Exception e){
            log.error("删除资源时出错",e);
            throw new MyRunTimeException(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

}

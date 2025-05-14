package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.exception.MyRunTimeException;
import com.example.myobjectserver.mapper.ResourceMapper;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.result.ResultCodeEnum;
import com.example.myobjectserver.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author KORE
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Value("${my.upload.file-upload-path}")
    private String uploadDir;
    private final ResourceMapper resourceMapper;
    private final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
    public ResourceServiceImpl(ResourceMapper resource){
        this.resourceMapper = resource;
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

    /**
     * 上传文件
     * @param file 上传文件
     * @param type 文件类型
     * @return 1 || 0
     * @throws IOException
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


    /**
     * 删除单个资源
     * @param id 资源id
     * @return true || false
     */
    @Override
    public Boolean deleteResourceById(Integer id) {
        Resource resource = resourceMapper.selectById(id);
        String fileName = resource.getPath().substring(resource.getPath().lastIndexOf("/") + 1);
        String filePath = System.getProperty("user.dir") + uploadDir + fileName;
        File file = new File(filePath);
        if(file.exists() && file.delete()){
            System.out.println("文件删除成功");
            resourceMapper.deleteById(resource.getId());
            return true;
        } else {
            throw new MyRunTimeException(ResultCodeEnum.USER_ERROR);
        }
    }

    /**
     * 删除多个资源
     * @param ids 数组id
     * @return true || false
     */
    @Override
    public Boolean deleteResourceByIdList(List<Integer> ids) {
        try {
            for (Resource resource : resourceMapper.selectByIds(ids)) {
                String fileName = resource.getPath().substring(resource.getPath().lastIndexOf("/") + 1);
                String filePath = System.getProperty("user.dir") + uploadDir + fileName;
                File file = new File(filePath);
                if(file.exists() && file.delete()){
                    resourceMapper.deleteById(resource.getId());
                } else {
                    throw new MyRunTimeException(ResultCodeEnum.FILE_UNKNOWN);
                }
            }
            return true;
        }catch (Exception e){
            logger.error("删除资源时出错",e);
            throw new MyRunTimeException(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

}

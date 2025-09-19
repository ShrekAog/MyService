package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.dto.ResourceDTOWrapper;
import com.example.myobjectserver.pojo.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author KORE
 */
public interface ResourceService extends IService<Resource> {
    /**
     * 获取所有资源
     * @param current 当前页
     * @param pageSize 一页几个
     * @return resource
     */
    Map<String,Object> getResourceList(Integer current, Integer pageSize);

    /**
     * 根据相册id获取对应的图片，屏蔽视频文件
     * @param classifyId 相册id
     * @param current 当前页
     * @param pageSize 一页几个
     * @return map.list
     */
    Map<String,Object> getResourceListByClassifyId(Integer classifyId,Integer current, Integer pageSize);

    List<Resource> getResourceVideoListByUserId(Integer userId);

    /**
     * 根据相册id获取图片数量
     * @param classifyId 相册id
     * @return count
     */
    Integer getResourceCount(Integer classifyId);
    /**
     * 解析文件并上传到服务器
     * @param file 上传文件
     * @param type 文件类型
     * @return 图片ID
     * @throws IOException IOException
     */
    Integer uploadResource(MultipartFile file,String type,Integer classify) throws IOException;

    List<Resource> uploadResource(ResourceDTOWrapper wrapper) throws IOException;

    /**
     * 根据资源id删除对应的资源
     * @param id 资源ID
     * @param userId 要删除哪位用户的资源
     * @return true || false
     */
    Integer deleteResourceById(Integer id,Integer userId);

    /**
     * 根据ids批量删除资源
     * @param ids 数组id
     * @return ture || false
     */
    Boolean deleteResourceByIdList(List<Integer> ids);

}

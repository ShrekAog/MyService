package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author KORE
 */
public interface ResourceService extends IService<Resource> {
    Map<String,Object> getResourceList(Integer current, Integer pageSize);
    /**
     * 解析文件并上传到服务器
     * @param file 上传文件
     * @param type 文件类型
     * @return Integer 1成功 0失败
     * @throws IOException IOException
     */
    Integer uploadResource(MultipartFile file,String type,Integer classify) throws IOException;

    /**
     * 根据资源id删除对应的资源
     * @param id 资源id
     * @return ture || false
     */
    Boolean deleteResourceById(Integer id);

    /**
     * 根据ids批量删除资源
     * @param ids 数组id
     * @return ture || false
     */
    Boolean deleteResourceByIdList(List<Integer> ids);

}

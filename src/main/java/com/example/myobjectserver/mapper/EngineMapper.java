package com.example.myobjectserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myobjectserver.pojo.Engine;
import com.example.myobjectserver.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author KORE
 */
@Mapper
public interface EngineMapper extends BaseMapper<Engine>{
    /**
     * 根据引擎资源id获取资源
     * @return 资源
     */
    Resource getResourceById(Integer resourceId);
}

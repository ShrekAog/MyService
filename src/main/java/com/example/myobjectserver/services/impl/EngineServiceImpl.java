package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.EngineMapper;
import com.example.myobjectserver.mapper.ResourceMapper;
import com.example.myobjectserver.pojo.Engine;
import com.example.myobjectserver.services.EngineService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KORE
 */
@Service
public class EngineServiceImpl extends ServiceImpl<EngineMapper, Engine> implements EngineService {

    private final ResourceMapper resourceMapper;
    public EngineServiceImpl(ResourceMapper resourceMapper){
        this.resourceMapper = resourceMapper;
    }

    @Override
    public List<Engine> getEngineList() {

        List<Engine> list = this.list();
        list.forEach(item -> {
            item.setResource(resourceMapper.selectById(item.getResourceId()));
        });
        return list;
    }
}

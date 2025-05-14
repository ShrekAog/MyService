package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.Engine;

import java.util.List;

/**
 * @author KORE
 */

public interface EngineService extends IService<Engine> {
    /**
     * 获取所有引擎列表
     * @return 引擎
     */
    List<Engine> getEngineList();
}

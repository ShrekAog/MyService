package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.UsersRouter;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
public interface UsersRouterService extends IService<UsersRouter> {
    /**
     * 查询所有路由表
     * @return 路由列表
     */
    List<UsersRouter> getRoutersList();
    /**
     * 新增路由表
     * @param router 路由表
     * @return 1成功，0失败
     */
    Integer addRouter(UsersRouter router);

    /**
     * 删除路由表
     * @param id 路由表id
     * @return 1成功，0失败
     */
    Integer removeRouterById(Integer id);

    /**
     * 更新路由表状态
     * @param router 路由表
     * @return 1成功，0失败
     */
    Integer updateRouterStatus(UsersRouter router);
}

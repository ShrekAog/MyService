package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.vo.RouterVo;

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
     * 根据用户类型返回对应的路由表
     * @param userType 用户类型
     * @return 用户路由列表
     */
    List<UsersRouter> getUserRoutersById(Integer userType);

    /**
     * 根据路由列表进行子菜单处理
     * @param routers 路由列表
     * @return 处理好之后的路由表
     */
    List<RouterVo> getUserRoutersVoByList(List<UsersRouter> routers);
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
     *根据路由对象修改数据
     * @param router 路由对象
     * @return 修改成功后的数量
     */
    Integer updateRouter(UsersRouter router);



}

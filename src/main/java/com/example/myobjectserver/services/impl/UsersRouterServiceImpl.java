package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.UsersRouterMapper;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.services.UsersRouterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
@Service
public class UsersRouterServiceImpl extends ServiceImpl<UsersRouterMapper, UsersRouter> implements UsersRouterService {

    private final UsersRouterMapper mapper;
    public UsersRouterServiceImpl(UsersRouterMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 获取全部用户路由
     * @return 路由菜单
     */
    @Override
    public List<UsersRouter> getRoutersList(){
        QueryWrapper<UsersRouter> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort_order");
        wrapper.eq("parent_id",'0');
        List<UsersRouter> routers = mapper.selectList(wrapper);
        routers.forEach(item -> {
            item.setChildren(mapper.getRouterChildren(item.getId()));
        });

        return routers;
    }

    /**
     * 新增路由
     * @param router 路由
     * @return 1成功，0失败
     */
    @Override
    public Integer addRouter(UsersRouter router) {
        return mapper.insert(router);
    }

    /**
     * 删除路由
     * @param id 路由id
     * @return 1成功，0失败
     * */
    @Override
    public Integer removeRouterById(Integer id) {
        return mapper.deleteById(id);
    }

    /**
     * 更新路由状态
     * @param router 路由表
     * @return true || false
     */
    @Override
    public Integer updateRouterStatus(UsersRouter router) {
        return mapper.updateById(router);
    }


}

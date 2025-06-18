package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.mapper.UsersMapper;
import com.example.myobjectserver.mapper.UsersRouterMapper;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.services.UsersRouterService;
import com.example.myobjectserver.vo.RouterVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
@Service
public class UsersRouterServiceImpl extends ServiceImpl<UsersRouterMapper, UsersRouter> implements UsersRouterService {

    private final UsersRouterMapper mapper;
    private final UsersMapper usersMapper;
    public UsersRouterServiceImpl(UsersRouterMapper mapper, UsersMapper usersMapper) {
        this.mapper = mapper;
        this.usersMapper = usersMapper;
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

    @Override
    public List<UsersRouter> getUserRoutersById(Integer userType) {
        return usersMapper.getUserRoutersById(userType);
    }

    @Override
    public List<RouterVo> getUserRoutersVoByList(List<UsersRouter> routers) {
        List<RouterVo> result = new ArrayList<>();
        // 先处理父级菜单
        for (UsersRouter item : routers) {
            // 只处理父级菜单
            if (item.getParentId() == 0) {
                RouterVo build = RouterVo.builder()
                        .id(item.getId())
                        .text(item.getText())
                        .path(item.getPath())
                        .iconPath(item.getIconPath())
                        .children(new ArrayList<>())
                        .build();
                result.add(build);
            }
        }

        // 再处理子菜单
        for (UsersRouter item : routers) {
            // 只处理子菜单
            if (item.getParentId() != 0) {
                // 找到对应的父菜单
                for (RouterVo parentVo : result) {
                    if (parentVo.getId() == Integer.valueOf(item.getParentId())) {
                        RouterVo childVo = RouterVo.builder()
                                .id(item.getId())
                                .text(item.getText())
                                .path(item.getPath())
                                .iconPath(item.getIconPath())
                                .parentId(Integer.valueOf(item.getParentId()))
                                .build();
                        parentVo.getChildren().add(childVo);
                    }
                }
            }
        }
        return result;
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
     *根据路由对象修改数据
     * @param router 路由对象
     * @return 修改成功后的数量
     */
    @Override
    public Integer updateRouter(UsersRouter router){
        return mapper.updateById(router);
    }

}

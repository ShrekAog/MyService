package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.Users;
import com.example.myobjectserver.pojo.UsersRouter;
import com.example.myobjectserver.vo.UserVo;
import com.example.myobjectserver.vo.UsersRoutersVo;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
public interface UsersService extends IService<Users> {
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
    List<UsersRoutersVo> getUserRoutersVoByList(List<UsersRouter> routers);

    /**
     *根据路由对象修改数据
     * @param router 路由对象
     * @return 修改成功后的数量
     */
    Integer updateRouters(UsersRouter router);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return user
     */
    Users login(String username, String password);

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return true|false
     */
    Boolean register(String username, String password);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return UserVo
     */
    UserVo getUserInfoByUsername(String username);
    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true|false
     */
    Boolean checkUsernameExist(String username);

}

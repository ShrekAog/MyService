package com.example.myobjectserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myobjectserver.pojo.Users;
import com.example.myobjectserver.pojo.UsersRouter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    /**
     * 根据用户类型返回对应的路由表
     * @param userType 用户类型
     * @return 路由列表
     */
    List<UsersRouter> getUserRoutersById(Integer userType);

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return true|false
     */
    @Select("select username from users where binary username = #{username}")
    Users checkUsernameExist(String username);
}

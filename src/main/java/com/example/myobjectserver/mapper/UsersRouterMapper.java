package com.example.myobjectserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myobjectserver.pojo.UsersRouter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 恒光
 * createTime:2024-11-29
 * version:1.0
 */
@Mapper
public interface UsersRouterMapper extends BaseMapper<UsersRouter> {
    List<UsersRouter> getRouterChildren(Integer parentId);
}

package com.example.myobjectserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myobjectserver.pojo.Note;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 恒光
 * createTime:2025-06-08
 * version:1.0
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
}

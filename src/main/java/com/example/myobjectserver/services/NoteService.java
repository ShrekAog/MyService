package com.example.myobjectserver.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myobjectserver.pojo.Note;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 恒光
 * createTime:2025-06-08
 * version:1.0
 */
public interface NoteService extends IService<Note> {
    /**
     * 根据用户名上传说说照片
     * @param files 照片列表
     * @param username 用户名
     * @return true || false
     */
    Boolean notesResource(List<MultipartFile> files, String username,Integer userId,Integer noteId) throws IOException;

    /**
     * 发布说说
     * @param note 对象
     * @return 说说主键
     */
    Integer publish(Note note);

    /**
     * 根据id获取说说
     * @param userId
     * @return 说说列表
     */
    List<Note> getNoteByUserId(Integer userId);
}

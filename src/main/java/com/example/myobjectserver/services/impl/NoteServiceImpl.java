package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.exception.MyRunTimeException;
import com.example.myobjectserver.mapper.NoteMapper;
import com.example.myobjectserver.mapper.NoteResourceMapper;
import com.example.myobjectserver.mapper.ResourcePathMapper;
import com.example.myobjectserver.pojo.Note;
import com.example.myobjectserver.pojo.NoteResource;
import com.example.myobjectserver.pojo.ResourcePath;
import com.example.myobjectserver.services.NoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 恒光
 * createTime:2025-06-08
 * version:1.0
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {
    @Value("${my.upload.notes-upload-path}")
    private String notesImageDir;

    private final NoteResourceMapper noteResourceMapper;
    private final ResourcePathMapper resourcePathMapper;
    private final NoteMapper noteMapper;
    public NoteServiceImpl(NoteResourceMapper noteResourceMapper, NoteMapper noteMapper, ResourcePathMapper resourcePathMapper) {
        this.resourcePathMapper = resourcePathMapper;
        this.noteResourceMapper = noteResourceMapper;
        this.noteMapper = noteMapper;
    }


    @Override
    public Integer publish(Note note) {
        noteMapper.insert(note);
        return note.getId();
    }

    @Override
    public List<Note> getNoteByUserId(Integer userId) {
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Note> noteList = noteMapper.selectList(wrapper);
        noteList.forEach(note -> {
            QueryWrapper<NoteResource> resourceQueryWrapper = new QueryWrapper<>();
            resourceQueryWrapper.eq("note_id", note.getId());
            List<NoteResource> resourceList = noteResourceMapper.selectList(resourceQueryWrapper);
            note.setResources(resourceList);
        });
        return noteList;
    }

    @Override
    public Boolean notesResource(List<MultipartFile> files,String username,Integer userId,Integer noteId) {
        QueryWrapper<ResourcePath> wrapper = new QueryWrapper<>();
        wrapper.eq("title","说说和日志的照片");
        wrapper.eq("user_id",userId);
        ResourcePath path = resourcePathMapper.selectOne(wrapper);
        String host = "https://api.konosubaz.cn:49327" + "/notesImages/" + username + "/";
        Integer pathId = 0;
        if(path == null){
            ResourcePath build = ResourcePath.builder()
                    .userId(userId)
                    .title("说说和日志的照片")
                    .introduction("默认存储相册")
                    .type("相册")
                    .build();
            resourcePathMapper.insert(build);
            pathId = build.getId();
        }else{
            pathId = path.getId();
        }

        File dir = new File(System.getProperty("user.dir") + notesImageDir + "/" + username);

        Integer finalPathId = pathId;

        files.forEach(file -> {
            String fileName = file.getOriginalFilename();
            String filePrefix = fileName.substring(0,fileName.indexOf("."));
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String fileUuid = UUID.randomUUID() + "." + fileSuffix;
            try {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file.transferTo(new File(System.getProperty("user.dir") + notesImageDir + "/" + username,fileUuid));
                NoteResource noteResource = NoteResource.builder()
                        .noteId(noteId)
                        .resourcePathId(finalPathId)
                        .imageUrl(host + fileUuid)
                        .build();
                noteResourceMapper.insert(noteResource);
            } catch (IOException e) {
                throw new MyRunTimeException(e);
            }
        });
        return true;
    }

}

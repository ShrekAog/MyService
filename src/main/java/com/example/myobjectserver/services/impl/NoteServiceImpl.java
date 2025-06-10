package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.exception.MyRunTimeException;
import com.example.myobjectserver.mapper.NoteMapper;
import com.example.myobjectserver.mapper.NoteResourceMapper;
import com.example.myobjectserver.pojo.Note;
import com.example.myobjectserver.services.NoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private final NoteMapper noteMapper;
    public NoteServiceImpl(NoteResourceMapper noteResourceMapper, NoteMapper noteMapper) {
        this.noteResourceMapper = noteResourceMapper;
        this.noteMapper = noteMapper;
    }


    @Override
    public Boolean publish(Note note) {
        return noteMapper.insert(note) > 0;
    }

    @Override
    public Boolean notesResource(List<MultipartFile> files,String username) throws IOException {
        File dir = new File(System.getProperty("user.dir") + notesImageDir + "/" + username);
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
            } catch (IOException e) {
                throw new MyRunTimeException(e);
            }
        });
        return true;
    }
}

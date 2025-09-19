package com.example.myobjectserver.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myobjectserver.exception.MyRunTimeException;
import com.example.myobjectserver.mapper.NoteMapper;
import com.example.myobjectserver.mapper.ResourceMapper;
import com.example.myobjectserver.mapper.ResourceNoteRelationMapper;
import com.example.myobjectserver.mapper.ResourcePathMapper;
import com.example.myobjectserver.pojo.Note;
import com.example.myobjectserver.pojo.Resource;
import com.example.myobjectserver.pojo.ResourceNoteRelation;
import com.example.myobjectserver.pojo.ResourcePath;
import com.example.myobjectserver.services.NoteService;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 恒光
 * createTime:2025-06-08
 * version:1.0
 */
@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {
    @Value("${my.upload.file-upload-path}")
    private String resourceImageDir;

    private final ResourcePathMapper resourcePathMapper;
    private final ResourceMapper resourceMapper;
    private final ResourceNoteRelationMapper resourceNoteRelationMapper;
    private final NoteMapper noteMapper;
    public NoteServiceImpl(NoteMapper noteMapper, ResourcePathMapper resourcePathMapper, ResourceMapper resourceMapper, ResourceNoteRelationMapper resourceNoteRelationMapper) {
        this.resourcePathMapper = resourcePathMapper;
        this.noteMapper = noteMapper;
        this.resourceMapper = resourceMapper;
        this.resourceNoteRelationMapper = resourceNoteRelationMapper;
    }


    @Override
    public Integer publish(Note note) {
        noteMapper.insert(note);
        return note.getId();
    }

    @Override
    public Map<String, Object> getNoteByUserId(Integer userId,Integer current,Integer pageSize) {
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .orderByDesc("create_time");

        Page<Note> page = new Page<>(current, pageSize);
        Page<Note> paged = noteMapper.selectPage(page,wrapper);
        List<Note> notes = paged.getRecords();

        if(notes.isEmpty()){
            return null;
        }

        List<Integer> noteIds = notes.stream()
                .map(Note::getId)
                .toList();

        List<ResourceNoteRelation> allRelations  = resourceNoteRelationMapper.selectList(
                new QueryWrapper<ResourceNoteRelation>().in("note_id", noteIds)
        );

        Map<Integer, List<ResourceNoteRelation>> relationMap  = allRelations.stream()
                .collect(Collectors.groupingBy(ResourceNoteRelation::getNoteId));

        List<Integer> allResourceIds = allRelations.stream()
                .map(ResourceNoteRelation::getResourceId)
                .toList();

        Map<Integer, Resource> resourceMap = allResourceIds.isEmpty() ?
                new HashMap<>() :
                resourceMapper.selectByIds(allResourceIds).stream()
                                .collect(Collectors.toMap(Resource::getId, Function.identity()));

        notes.forEach(note -> {
            List<ResourceNoteRelation> relations = relationMap.getOrDefault(note.getId(), Collections.emptyList());
            List<Resource> resourceList = relations.stream()
                    .map(ResourceNoteRelation::getResourceId)
                    .map(resourceMap::get)
                    .filter(Objects::nonNull)
                    .toList();
            note.setResources(resourceList);
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("current", paged.getCurrent());
        map.put("total", paged.getTotal());
        map.put("data", notes);
        return map;
    }

    @Override
    public Map<String,Object> getNoteResourceByUserId(Integer userId, Integer current, Integer pageSize) {
        LambdaQueryWrapper<ResourcePath> pathWrapper = new LambdaQueryWrapper<>();
        pathWrapper
                .eq(ResourcePath::getUserId, userId)
                .eq(ResourcePath::getTitle,"说说和日志的照片");

        ResourcePath path = resourcePathMapper.selectOne(pathWrapper);


        MPJLambdaQueryWrapper<Resource> wrapper = new MPJLambdaQueryWrapper<>();
        wrapper.selectAll(Resource.class)
                .innerJoin(" resource_path rp on rp.id = resource_path_id")
                .eq(Resource::getUserId, userId)
                .eq(Resource::getResourcePathId,path.getId())
                .orderByDesc(Resource::getCreateTime)
                .orderByDesc(Resource::getId)
                .notLike(Resource::getPath,".mp4");

        Page<Resource> page = new Page<>(current, pageSize);
        Page<Resource> paged = resourceMapper.selectPage(page, wrapper);
        List<Resource> resources = paged.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("current", paged.getCurrent());
        map.put("total", paged.getTotal());
        map.put("list", resources);
        return map;
    }

    @Override
    public Boolean notesResource(List<MultipartFile> files,String username,Integer userId,Integer noteId) {
        QueryWrapper<ResourcePath> wrapper = new QueryWrapper<>();
        wrapper.eq("title","说说和日志的照片");
        wrapper.eq("user_id",userId);
        ResourcePath path = resourcePathMapper.selectOne(wrapper);
        //String host = "https://api.konosubaz.cn:49327" + "/notesImages/" + username + "/";

        Integer pathId;
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

        File dir = new File(System.getProperty("user.dir") + resourceImageDir + "/" + username);

        Integer finalPathId = pathId;

        files.forEach(file -> {
            String fileName = file.getOriginalFilename();
            String filePrefix = fileName.substring(0,fileName.indexOf("."));
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String fileUuid = UUID.randomUUID() + "." + fileSuffix;
            String host = "http://192.168.1.220:8080"+ resourceImageDir + username + "/" + fileUuid;
            try {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file.transferTo(new File(dir,fileUuid));
                int size = (int) file.getSize();
                String mimeType = file.getContentType();
                Resource fileBuilder = Resource.builder()
                        .id(null)
                        .userId(userId)
                        .type("wallpaper")
                        .path(host)
                        .size(size)
                        .originalName(filePrefix)
                        .mimeType(mimeType)
                        .status(true)
                        .storeType("local")
                        .resourcePathId(finalPathId)
                        .createTime(null)
                        .build();
                resourceMapper.insert(fileBuilder);
                int resourceId = fileBuilder.getId();

                ResourceNoteRelation relation = ResourceNoteRelation.builder()
                        .noteId(noteId)
                        .resourceId(resourceId)
                        .build();
                resourceNoteRelationMapper.insert(relation);

            } catch (IOException e) {
                throw new MyRunTimeException(e);
            }
        });
        return true;
    }
}

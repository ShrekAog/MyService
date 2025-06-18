package com.example.myobjectserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myobjectserver.pojo.Note;
import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.NoteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 恒光
 * createTime:2025-06-08
 * version:1.0
 */
@RestController
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @PostMapping("/notesImage")
    public R<Boolean> uploadNotesImage(@RequestParam("files") List<MultipartFile> files
            , @RequestParam("username") String username
            ,@RequestParam("userId") Integer userId
            ,@RequestParam("noteId") Integer noteId) throws IOException {
        return R.ok(noteService.notesResource(files,username,userId,noteId));
    }
    @PostMapping("/add")
    public R<Integer> addNote(@RequestBody Note note) {
        return R.ok(noteService.publish(note));
    }
    @GetMapping("list")
    public R<List<Note>> getAllNotes() {
        return R.ok(noteService.list());
    }
    @GetMapping("get/{userId}")
    public R<List<Note>> getNoteByUserId(@PathVariable Integer userId) {
        return R.ok(noteService.getNoteByUserId(userId));
    }
}

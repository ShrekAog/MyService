package com.example.myobjectserver.controller;

import com.example.myobjectserver.result.R;
import com.example.myobjectserver.services.NoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public R<Boolean> uploadNotesImage(@RequestParam("files") List<MultipartFile> files, @RequestParam("username") String username) throws IOException {
        return R.ok(noteService.notesResource(files,username));
    }
}

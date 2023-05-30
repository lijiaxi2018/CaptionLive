package com.aguri.captionlive.controller;


import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileRecordService fileRecordService;

    @GetMapping("/{fileRecordId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileRecordId) {
        return fileRecordService.download(fileRecordId);
    }

    @PostMapping()
    public ResponseEntity<Resp> uploadAvatar(@RequestParam MultipartFile file) {
        Long fileRecordId = fileRecordService.uploadSmallSizeFile(file);
        return ResponseEntity.ok(Resp.ok(fileRecordId));
    }

}

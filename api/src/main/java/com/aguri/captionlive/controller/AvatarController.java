package com.aguri.captionlive.controller;


import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Autowired
    private FileRecordService fileRecordService;

    @GetMapping("/{fileRecordId}")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long fileRecordId) {
        FileRecord fileRecord = fileRecordService.getFileRecordById(fileRecordId);
        String filePath = fileRecord.getPath();
        String storedName = fileRecord.getStoredName();
        Resource fileResource = new FileSystemResource(filePath + File.separator + storedName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(fileRecord.getType()));
        return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
    }
}

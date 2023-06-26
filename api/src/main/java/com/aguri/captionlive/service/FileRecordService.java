package com.aguri.captionlive.service;
import com.aguri.captionlive.model.FileRecord;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileRecordService {

    FileRecord getFileRecordById(Long id);

    List<FileRecord> getAllFileRecords();

    FileRecord saveFileRecord(FileRecord fileRecord);

    void deleteFileRecord(Long id);

    void updateFileRecord(Long id,FileRecord fileRecord);

    ResponseEntity<Resource> download(Long fileRecordId);

    Long uploadSmallSizeFile(MultipartFile file);

    Long uploadLargeFile(MultipartFile file);
}
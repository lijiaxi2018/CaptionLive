package com.aguri.captionlive.service;
import com.aguri.captionlive.model.FileRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileRecordService {

    FileRecord getFileRecordById(Long id);

    List<FileRecord> getAllFileRecords();

    FileRecord saveFileRecord(FileRecord fileRecord);

    void deleteFileRecord(Long id);

    FileRecord saveFile(MultipartFile file,String logicalDirectory) throws IOException;

}
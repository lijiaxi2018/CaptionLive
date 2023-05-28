package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.repository.FileRecordRepository;
import com.aguri.captionlive.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileRecordServiceImpl implements FileRecordService {

    @Autowired
    private FileRecordRepository fileRecordRepository;

    @Value("${spring.servlet.multipart.location}")
    private String fileStorageDirectory;

    @Override
    public FileRecord getFileRecordById(Long id) {
        return fileRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FileRecord not found with id: " + id));
    }

    @Override
    public List<FileRecord> getAllFileRecords() {
        return fileRecordRepository.findAll();
    }

    @Override
    public FileRecord saveFileRecord(FileRecord fileRecord) {
        return fileRecordRepository.save(fileRecord);
    }

    @Override
    public void deleteFileRecord(Long id) {
        fileRecordRepository.deleteById(id);
    }

    // 其他自定义的服务方法的实现...


    @Override
    public FileRecord saveSmallSizeFile(MultipartFile file, String logicalDirectory) throws IOException {
        String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String storedName = UUID.randomUUID().toString().replaceAll("-", "");
        String filePath = fileStorageDirectory + File.separator + logicalDirectory;

        // Save the file to the storage directory
        String path = filePath + File.separator + storedName;
        File directory = new File(path);
        if (!directory.exists()) {
            boolean mkdirs = directory.mkdirs();
        }

        Path storagePath = Path.of(path);
        Files.copy(file.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);
        String contentType = file.getContentType();

        FileRecord fileRecord = new FileRecord();
        fileRecord.setOriginalName(originalName);
        fileRecord.setStoredName(storedName);
        fileRecord.setType(contentType);
        fileRecord.setPath(filePath);

        return fileRecordRepository.save(fileRecord);
    }
    @Override
    public ResponseEntity<Resource> download(FileRecord fileRecord) {
        String filePath = fileRecord.getPath();
        String storedName = fileRecord.getStoredName();
        Resource fileResource = new FileSystemResource(filePath + File.separator + storedName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(fileRecord.getType()));
        return new ResponseEntity<>(fileResource, headers, HttpStatus.OK);
    }

}
package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "file_records")
public class FileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FileRecordId;

    private String originalName;

    private String storedName;

    private String path;

    private String type;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;

    public String getSuffix() {
        return getOriginalName().split("\\.")[1];
    }

    public static FileRecord generateFileRecord(Long id) {
        FileRecord fileRecord = null;
        if (id != 0L) {
            fileRecord = new FileRecord();
            fileRecord.setFileRecordId(id);
        }
        return fileRecord;
    }

}

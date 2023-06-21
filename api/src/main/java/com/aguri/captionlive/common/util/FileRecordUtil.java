package com.aguri.captionlive.common.util;

import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.repository.FileRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileRecordUtil {
    private static FileRecordRepository fileRecordRepository;

    @Autowired
    public void setFileRecordRepository(FileRecordRepository fileRecordRepository) {
        FileRecordUtil.fileRecordRepository = fileRecordRepository;
    }

    public static FileRecord generateFileRecord(Long id) {
        FileRecord f = null;
        if (id != 0L) {
            f = fileRecordRepository.getReferenceById(id);
        }
        return f;
    }

    public static Long generateFileRecordId(FileRecord fileRecord) {
        if (fileRecord == null) {
            return 0L;
        }
        return fileRecord.getFileRecordId();
    }
}

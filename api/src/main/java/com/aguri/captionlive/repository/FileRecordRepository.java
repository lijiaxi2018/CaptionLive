package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRecordRepository extends JpaRepository<FileRecord,Long> {

}

package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {
    Remark findOneBySegmentId(Long segmentId);
}

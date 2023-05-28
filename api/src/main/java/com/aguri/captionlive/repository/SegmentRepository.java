package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
    List<Segment> findAllByProjectId(Long projectId);
}

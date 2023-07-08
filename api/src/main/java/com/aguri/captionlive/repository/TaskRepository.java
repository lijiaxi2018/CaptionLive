package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllBySegmentSegmentIdIn(List<Long> segmentIds);

    List<Task> findAllBySegmentSegmentId(Long segmentId);
}

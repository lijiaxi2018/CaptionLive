package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.repository.SegmentRepository;
import com.aguri.captionlive.repository.TaskRepository;
import com.aguri.captionlive.service.SegmentService;
import com.aguri.captionlive.service.TaskService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SegmentServiceImpl implements SegmentService {
    private final SegmentRepository segmentRepository;

    @Autowired
    public SegmentServiceImpl(SegmentRepository segmentRepository) {
        this.segmentRepository = segmentRepository;
    }

    @Override
    public Segment saveSegment(Segment segment) {
        return segmentRepository.save(segment);
    }

    @Override
    public Segment updateSegment(Long id, Segment segment) {
        Segment existingSegment = segmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Segment not found with id: " + id));

        // Update the segment properties
        existingSegment.setIsGlobal(segment.getIsGlobal());
        existingSegment.setSummary(segment.getSummary());
        existingSegment.setScope(segment.getScope());
        // Update other properties as needed

        // Save the updated segment
        return segmentRepository.save(existingSegment);
    }

    @Override
    public Segment getSegmentById(Long segmentId) {
        Optional<Segment> segmentOptional = segmentRepository.findById(segmentId);
        return segmentOptional
                .orElseThrow(() -> new EntityNotFoundException("Segment not found with id: " + segmentId));

    }

    @Override
    public List<Segment> getAllSegments() {
        return segmentRepository.findAll();
    }

    @Override
    public void deleteSegment(Long segmentId) {
        List<Task> tasks = taskService.findAllBySegmentSegmentId(segmentId);
        System.out.println("deleteSegment flush");
        //entityManager.flush();
        taskService.deleteAllInBatch(tasks);
        segmentRepository.deleteById(segmentId);
    }

    @Override
    public List<Segment> getAllSegments(Long projectId) {
        return segmentRepository.findAllByProjectProjectId(projectId);
    }

    @Autowired
    TaskService taskService;

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public void deleteAllInBatch(List<Segment> segments) {
        List<Long> segmentIds = segments.stream().map(Segment::getSegmentId).toList();
        List<Task> tasks = taskService.findAllInSegmentSegmentId(segmentIds);
        System.out.println("deleteAllInBatch Segment flush");
        //entityManager.flush();
        taskService.deleteAllInBatch(tasks);
        //segmentRepository.deleteAllInBatch(segments);
    }
}

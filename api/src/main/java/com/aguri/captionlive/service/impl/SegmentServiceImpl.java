package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.SegmentRepository;
import com.aguri.captionlive.service.SegmentService;
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
        existingSegment.setProjectId(segment.getProjectId());
        existingSegment.setIsGlobal(segment.getIsGlobal());
        existingSegment.setSummary(segment.getSummary());
        existingSegment.setBeginTime(segment.getBeginTime());
        existingSegment.setEndTime(segment.getEndTime());
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
        segmentRepository.deleteById(segmentId);
    }
    @Override
    public List<Segment> getAllSegments(Long projectId) {
        return segmentRepository.findAllByProjectId(projectId);
    }
}

package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.model.User;

import java.util.List;

public interface SegmentService {
    Segment saveSegment(Segment segment);

    Segment updateSegment(Long id, Segment segment);

    Segment getSegmentById(Long segmentId);

    List<Segment> getAllSegments();

    void deleteSegment(Long segmentId);
    List<Segment> getAllSegments(Long projectId);

    void deleteAllInBatch(List<Segment> segments);
}

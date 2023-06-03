package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {
    private Long projectId;

    private Long sourceFileRecordId;

    private String fileName;

    private String name;

    private Boolean isPublic;

    private OperatorRequest Operator;

    @Enumerated(EnumType.ORDINAL)
    private Project.Type type;

    private RemarkRequest remarkRequest;

    private List<SegmentRequest> segmentRequests;

    @Data
    public static class SegmentRequest {
        private Long segmentId;

        private String summary;

        private Integer beginTime;

        private Integer endTime;

        @Enumerated(EnumType.ORDINAL)
        private List<Task.Workflow> workflows;

        private RemarkRequest RemarkRequest;
    }

    @Data
    public static class RemarkRequest {
        private String content;
    }

    @Data
    public static class OperatorRequest {
        private Long userId;
    }
}

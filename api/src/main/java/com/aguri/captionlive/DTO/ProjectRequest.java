package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Task;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {
    private Long projectId;

    private Long organizationId;

    private Long sourceFileRecordId;

    private String fileName;

    private String name;

    private Boolean isPublic;

    private Long operatorId;

    private Project.Type type;

    private RemarkRequest remarkRequest;

    private List<SegmentRequest> segmentRequests;

    @Data
    public static class SegmentRequest {
        private Long segmentId;

        private String summary;

        private String scope;

        private List<Task.Workflow> workflows;

        private RemarkRequest RemarkRequest;
    }

    @Data
    public static class RemarkRequest {
        private String content;
    }
}

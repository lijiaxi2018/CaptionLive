package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Remark;
import com.aguri.captionlive.model.Task;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class ProjectCreateRequest {
    private String fileName;

    private String name;

    private Boolean isPublic;

    @Enumerated(EnumType.ORDINAL)
    private Project.Type type;

    private RemarkCreateRequest remarkCreateRequest;

    private List<SegmentCreateRequest> segmentCreateRequests;

    @Data
    public static class SegmentCreateRequest {
        private String summary;

        private Integer beginTime;

        private Integer endTime;

        @Enumerated(EnumType.ORDINAL)
        private List<Task.Workflow> workflows;

        private RemarkCreateRequest RemarkCreateRequest;
    }

    @Data
    public static class RemarkCreateRequest {
        private String content;
        private Long userId;
    }
}

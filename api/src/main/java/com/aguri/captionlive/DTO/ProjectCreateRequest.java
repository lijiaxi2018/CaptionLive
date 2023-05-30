package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Project;
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

    //TODO REMARK

    private List<SegmentCreateRequest> segmentCreateRequests;

    @Data
    public static class SegmentCreateRequest {
        private String summary;

        private Integer beginTime;

        private Integer endTime;

        @Enumerated(EnumType.ORDINAL)
        private List<Task.Workflow> workflows;

        //TODO REMARK
    }
}

package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Task;
import lombok.Data;

import java.util.List;

@Data
public class ProjectInfo {

    private String title;

    private SegmentInfo overview;

    private Boolean isCompleted;

    private List<SegmentInfo> segmentInfos;

    @Data
    public static class SegmentInfo {

        private String summary;

        private Integer beginTime;

        private Integer endTime;

        private List<TaskInfo> taskInfos;

        @Data
        public static class TaskInfo {
            private String workerName;
            private Boolean hasUploadedFile;
            private Task.Status status;
        }


    }
}

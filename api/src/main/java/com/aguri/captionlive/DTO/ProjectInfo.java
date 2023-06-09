package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Remark;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectInfo {

    private Long projectId;

    private Boolean isCompleted;

    private String name;

    private LocalDateTime createdTime;

    private List<SegmentInfo.TaskInfo> taskInfos;

    private List<SegmentInfo> segmentInfos;

    @Data
    public static class SegmentInfo {

        private Long segmentId;

        private String summary;

        private String scope;

        private List<TaskInfo> taskInfos;

        private List<Remark> remarks;

        @Data
        public static class TaskInfo {

            private Long taskId;

            private User workerUser;

            private Long fileId;

            private Task.Workflow type;

            private Task.Status status;
        }


    }
}

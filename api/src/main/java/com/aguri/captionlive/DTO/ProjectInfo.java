package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Remark;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectInfo {

    private Long organizationId;

    private Boolean isCompleted;

    private String name;

    private LocalDateTime createdTime;

    private List<SegmentInfo.TaskInfo> taskInfos;

    private List<SegmentInfo> segmentInfos;

    @Data
    public static class SegmentInfo {

        private String summary;

        private Integer beginTime;

        private Integer endTime;

        private List<TaskInfo> taskInfos;

        private List<Remark> remarks;

        @Data
        public static class TaskInfo {

            private User workerUser;

            private Long fileId;

            private Task.Status status;
        }


    }
}

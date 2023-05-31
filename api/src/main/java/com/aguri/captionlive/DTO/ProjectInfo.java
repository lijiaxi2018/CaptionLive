package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Remark;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.model.User;
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

        private List<Remark> remarks;

        @Data
        public static class TaskInfo {

            private User workerUser;

            private Boolean hasUploadedFile;

            private Task.Status status;
        }


    }
}

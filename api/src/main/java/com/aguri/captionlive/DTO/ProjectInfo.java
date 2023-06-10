package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static List<ProjectInfo> generateProjectInfos(List<Project> projects) {
        return projects.stream().map(ProjectInfo::generateProjectInfo).toList();
    }

    public static ProjectInfo generateProjectInfo(Project project) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(project.getProjectId());
        projectInfo.setIsCompleted(true);
        projectInfo.setName(project.getName());
        List<Segment> segments = project.getSegments();
        projectInfo.setCreatedTime(project.getCreatedTime());
        projectInfo.setSegmentInfos(new ArrayList<>());
        segments.forEach(segment -> {
            List<SegmentInfo.TaskInfo> taskInfos = segment.getTasks().stream().map(task -> {
                SegmentInfo.TaskInfo taskInfo = new SegmentInfo.TaskInfo();
                taskInfo.setWorkerUser(task.getWorker());
                Task.Status status = task.getStatus();
                if (status != Task.Status.COMPLETED) {
                    projectInfo.setIsCompleted(false);
                }
                taskInfo.setStatus(status);
                FileRecord fileRecord = task.getFile();
                Long fileRecordId = 0L;
                if (fileRecord != null) {
                    fileRecordId = fileRecord.getFileRecordId();
                }
                taskInfo.setFileId(fileRecordId);
                taskInfo.setTaskId(task.getTaskId());
                return taskInfo;
            }).toList();
            if (segment.getIsGlobal()) {
                projectInfo.setTaskInfos(taskInfos);
            } else {
                SegmentInfo segmentInfo = new SegmentInfo();
                segmentInfo.setSegmentId(segment.getSegmentId());
                segmentInfo.setSummary(segment.getSummary());
                segmentInfo.setScope(segment.getScope());
                segmentInfo.setRemarks(segment.getRemarks());
                segmentInfo.setTaskInfos(taskInfos);
                projectInfo.getSegmentInfos().add(segmentInfo);
            }
        });
        return projectInfo;
    }
}

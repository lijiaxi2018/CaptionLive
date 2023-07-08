package com.aguri.captionlive.DTO;

import com.aguri.captionlive.common.util.FileRecordUtil;
import com.aguri.captionlive.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class ProjectInfo {

    private Long projectId;

    private Boolean isCompleted;

    private Long coverFileRecordId;

    private String name;

    private LocalDateTime createdTime;

    private List<SegmentInfo> segmentInfos;

    @Data
    public static class SegmentInfo {

        private Long segmentId;

        private String summary;

        private String scope;

        private Boolean isGlobal;

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
        FileRecord coverFileRecord = project.getCoverFileRecord();
        if (coverFileRecord != null) {
            projectInfo.setCoverFileRecordId(coverFileRecord.getFileRecordId());
        } else {
            projectInfo.setCoverFileRecordId(0L);
        }
        projectInfo.setName(project.getName());
        List<Segment> segments = project.getSegments();
        projectInfo.setCreatedTime(project.getCreatedTime());
        projectInfo.setSegmentInfos(new ArrayList<>());
        segments.forEach(segment -> {
            List<SegmentInfo.TaskInfo> taskInfos = segment.getTasks().stream().sorted(Comparator.comparingInt(t -> t.getType().getValue())).map(task -> {
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
                taskInfo.setType(task.getType());
                return taskInfo;
            }).toList();
            SegmentInfo segmentInfo = new SegmentInfo();
            segmentInfo.setSegmentId(segment.getSegmentId());
            segmentInfo.setSummary(segment.getSummary());
            segmentInfo.setScope(segment.getScope());
            segmentInfo.setRemarks(segment.getRemarks());
            segmentInfo.setTaskInfos(taskInfos);
            segmentInfo.setIsGlobal(segment.getIsGlobal());
            projectInfo.getSegmentInfos().add(segmentInfo);
        });
        return projectInfo;
    }
}

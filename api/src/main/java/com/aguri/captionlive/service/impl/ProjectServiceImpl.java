package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.ProjectRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.ProjectService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.aguri.captionlive.model.Task.AUDIO_AND_VIDEO_DEFAULT_WORKFLOWS;
import static com.aguri.captionlive.model.Task.TXT_DEFAULT_WORKFLOWS;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private FileRecordService fileRecordService;

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id:" + projectId));
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    @Override
    public Project createProject(ProjectRequest projectCreateRequest) {
        Project project = generateProject(projectCreateRequest);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Project updateProject(ProjectRequest projectRequest) {
        entityManager.flush();
        Project existingProject = generateUpdatedProject(projectRequest);
        return projectRepository.save(existingProject);
    }

    @Override
    public List<User> getAllAccessibleUsers(Long projectId) {
        return getProjectById(projectId).getAccessibleUsers();
    }

    @Override
    public List<Organization> getAllAccessibleOrganizations(Long projectId) {
        return organizationRepository.findAllByProjectsProjectId(projectId);
    }

    @Override
    public List<Project> getAllPublicProjects() {
        return projectRepository.findAllByIsPublic(true);
    }

    private Project generateUpdatedProject(ProjectRequest projectRequest) {
        Long projectId = projectRequest.getProjectId();
        Project existingProject = getProjectById(projectId);

        updateProjectSegments(projectRequest, existingProject);

        updateProjectSourceFileAndDesiredFileName(existingProject, projectRequest.getFileName(), projectRequest.getSourceFileRecordId());

        existingProject.setName(projectRequest.getName());

        existingProject.setIsPublic(projectRequest.getIsPublic());

        existingProject.setType(projectRequest.getType());

        return existingProject;
    }

    private void updateProjectSourceFileAndDesiredFileName(Project existingProject, String desiredFileName, Long sourceFileRecordId) {
        updateProjectSourceFile(existingProject, sourceFileRecordId);
        updateDesiredFileNameIfDesiredFileNotNull(existingProject.getSourceFileRecord(), desiredFileName);
    }

    private static void updateDesiredFileNameIfDesiredFileNotNull(FileRecord sourceFileRecord, String desiredFileName) {
        if (desiredFileName != null) {
            updateDesiredFileName(sourceFileRecord, desiredFileName);
        }
    }

    private void updateProjectSegments(ProjectRequest projectRequest, Project existingProject) {
        List<Segment> updatedSegments = generateUpdatedAndDeletedSegments(projectRequest, existingProject, existingProject.getSegments());
        List<Segment> appendingSegments = generateProjectSubSegments(existingProject,
                projectRequest.getSegmentRequests().stream().filter(segmentRequest -> segmentRequest.getSegmentId() == null).toList(),
                projectRequest.getOperator());
        ArrayList<Segment> segments = new ArrayList<>(appendingSegments);
        segments.addAll(updatedSegments);
        existingProject.setSegments(segments);
    }

    private static void updateDesiredFileName(FileRecord fileRecord, String desiredFileName) {
        String suffix = fileRecord.getSuffix();
        fileRecord.setOriginalName(desiredFileName + "." + suffix);
    }

    private void updateProjectSourceFile(Project existingProject, Long newSourceFileRecordId) {
        if (!Objects.equals(existingProject.getSourceFileRecord().getFileRecordId(), newSourceFileRecordId)) {
            FileRecord newFileRecord = fileRecordService.getFileRecordById(newSourceFileRecordId);
            existingProject.setSourceFileRecord(newFileRecord);
        }
    }


    @Autowired
    SegmentRepository segmentRepository;

    @Autowired
    EntityManager entityManager;

    private List<Segment> generateUpdatedAndDeletedSegments(ProjectRequest projectRequest, Project existingProject, List<Segment> existingSegments) {
        //        update and delete
        Map<Long, ProjectRequest.SegmentRequest> segmentUpdateRequestMap = projectRequest.getSegmentRequests().stream().filter(segmentRequest -> segmentRequest.getSegmentId() != null).collect(Collectors.toMap(ProjectRequest.SegmentRequest::getSegmentId, Function.identity()));
        List<Segment> needDeletingSegments = existingSegments.stream().filter(existingSegment -> !segmentUpdateRequestMap.containsKey(existingSegment.getSegmentId()) && !existingSegment.getIsGlobal()).toList();
        List<Segment> hasDeletedSegments = new ArrayList<>(existingSegments.stream().filter(existingSegment -> segmentUpdateRequestMap.containsKey(existingSegment.getSegmentId()) || existingSegment.getIsGlobal()).toList());
        segmentRepository.deleteAllInBatch(needDeletingSegments);

        return hasDeletedSegments.stream()
                .peek(existingSegment -> {
                    Long segmentId = existingSegment.getSegmentId();
                    if (existingSegment.getIsGlobal()) {
                        updateSegmentOriginalRemark(existingSegment, projectRequest.getRemarkRequest(), projectRequest.getOperator());
                        updateGlobalSegmentTasksIfProjectTypeChange(projectRequest, existingProject, existingSegment);
                    } else {
//                        updating The sub-segment
                        ProjectRequest.SegmentRequest segmentRequest = segmentUpdateRequestMap.get(segmentId);
                        existingSegment.setSummary(segmentRequest.getSummary());
                        existingSegment.setBeginTime(segmentRequest.getBeginTime());
                        existingSegment.setEndTime(segmentRequest.getEndTime());

                        updateSegmentOriginalRemark(existingSegment, segmentRequest.getRemarkRequest(), projectRequest.getOperator());
                        updateSegmentTasks(existingSegment, segmentRequest.getWorkflows());
                    }
                }).toList();
    }

    private void updateGlobalSegmentTasksIfProjectTypeChange(ProjectRequest projectRequest, Project existingProject, Segment existingSegment) {
        if (projectRequest.getType() != existingProject.getType()) {
            updateSegmentTasks(existingSegment, List.of(generateWorkflowsByProjectType(projectRequest.getType())));
        }
    }

    private static void updateSegmentOriginalRemark(Segment existingSegment, ProjectRequest.RemarkRequest remarkRequest, ProjectRequest.OperatorRequest operator) {
        Remark existingRemark = existingSegment.getRemarks().get(0);
        existingRemark.setContent(remarkRequest.getContent());
        User user = new User();
        user.setUserId(operator.getUserId());
        existingRemark.setUser(user);
    }

    @Autowired
    private TaskRepository taskRepository;

    private void updateSegmentTasks(Segment existingSegment, List<Task.Workflow> updatingWorkflows) {
        Set<Task.Workflow> updatingWorkflowSet = new HashSet<>(updatingWorkflows);
        List<Task> existingTasks = existingSegment.getTasks();
//        The existing tasks do not belong to this set, which means that these tasks need to be removed.
//        delete tasks
        List<Task> needDeletingTasks = existingTasks.stream().filter(task -> !updatingWorkflowSet.contains(task.getType())).toList();
        taskRepository.deleteAllInBatch(needDeletingTasks);
        List<Task> hasDeletedTasks = new ArrayList<>(existingTasks.stream().filter(task -> updatingWorkflowSet.contains(task.getType())).toList());

        Set<Task.Workflow> existingWorkflowSet = existingTasks.stream().map(Task::getType).collect(Collectors.toSet());
//        create tasks
        List<Task> appendingTasks = updatingWorkflows.stream().filter(updatingWorkflow -> !existingWorkflowSet.contains(updatingWorkflow)).map(workflow -> generateTask(existingSegment, workflow)).toList();
//        tasks could not be modified in this request;
        hasDeletedTasks.addAll(appendingTasks);
        existingSegment.setTasks(hasDeletedTasks);
    }

    private static Task generateTask(Segment existingSegment, Task.Workflow workflow) {
        Task task = new Task();
        task.setStatus(Task.Status.NOT_ASSIGNED);
        task.setType(workflow);
        task.setSegment(existingSegment);
        return task;
    }

    private Task.Workflow[] generateWorkflowsByProjectType(Project.Type type) {
        switch (type) {
            case TXT -> {
                return TXT_DEFAULT_WORKFLOWS;
            }
            case AUDIO_AND_VIDEO -> {
                return AUDIO_AND_VIDEO_DEFAULT_WORKFLOWS;
            }
            default -> throw new RuntimeException("unknown project type: " + type);
        }
    }

    private List<Task> generateTasksForGlobalSegmentByProjectType(Segment globalSegment, Project.Type type) {
        return Arrays.stream(generateWorkflowsByProjectType(type)).map(t -> Task.createTaskBySegmentAndWorkFlow(globalSegment, t)).toList();
    }

    private Project generateProject(ProjectRequest projectRequest) {
        Project project = new Project();
        Long sourceRecordFileId = projectRequest.getSourceFileRecordId();
        FileRecord fileRecord = fileRecordService.getFileRecordById(sourceRecordFileId);
        Organization organization = organizationService.getOrganizationById(projectRequest.getOrganizationId());

        updateDesiredFileNameIfDesiredFileNotNull(fileRecord, projectRequest.getFileName());

        project.setSourceFileRecord(fileRecord);

        project.setName(projectRequest.getName());

        project.setIsPublic(projectRequest.getIsPublic());

        project.setType(projectRequest.getType());

        project.setOrganizations(new ArrayList<>(List.of(organization)));

        List<Segment> subSegments = generateProjectSubSegments(project, projectRequest.getSegmentRequests(), projectRequest.getOperator());

        Segment globalSegment = generateGlobalSegment(project, projectRequest);

        ArrayList<Segment> segments = new ArrayList<>(subSegments);
        segments.add(globalSegment);
        project.setSegments(segments);

        return project;
    }

    private Segment generateGlobalSegment(Project project, ProjectRequest projectRequest) {
        Segment globalSegment = new Segment();
        ProjectRequest.RemarkRequest remarkRequest = projectRequest.getRemarkRequest();

        globalSegment.setProject(project);

        globalSegment.setIsGlobal(true);

        globalSegment.setRemarks(List.of(generateRemark(globalSegment, remarkRequest, projectRequest.getOperator())));

        globalSegment.setTasks(generateTasksForGlobalSegmentByProjectType(globalSegment, projectRequest.getType()));

        return globalSegment;
    }


    private Remark generateRemark(Segment segment, ProjectRequest.RemarkRequest remarkRequest, ProjectRequest.OperatorRequest createBy) {
        User user = new User();
        Remark remark = new Remark();

        user.setUserId(createBy.getUserId());

        remark.setSegment(segment);

        remark.setUser(user);

        remark.setContent(remarkRequest.getContent());

        return remark;
    }

    private List<Segment> generateProjectSubSegments(Project existingProject, List<ProjectRequest.SegmentRequest> segmentRequests, ProjectRequest.OperatorRequest createBy) {
        return segmentRequests.stream().map(segmentRequest -> {
            Segment segment = new Segment();

            segment.setProject(existingProject);

            segment.setSummary(segmentRequest.getSummary());

            segment.setBeginTime(segmentRequest.getBeginTime());

            segment.setEndTime(segmentRequest.getEndTime());

            segment.setRemarks(new ArrayList<>(List.of(generateRemark(segment, segmentRequest.getRemarkRequest(), createBy))));

            segment.setIsGlobal(false);

            segment.setTasks(segmentRequest.getWorkflows().stream().map(workflow -> generateTask(segment, workflow)).toList());
            return segment;
        }).toList();
    }

}

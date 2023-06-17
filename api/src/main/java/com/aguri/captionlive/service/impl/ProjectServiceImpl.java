package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.DTO.ProjectRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.common.util.FileRecordUtil;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.ProjectService;
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

    @Autowired
    private FileRecordRepository fileRecordRepository;

    @Autowired
    OwnershipRepository ownershipRepository;

    @Autowired
    AccessRepository accessRepository;

    @Autowired
    RemarkRepository remarkRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found with id:" + projectId));
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional
    public Project createProject(ProjectRequest projectRequest) {

        Project project = createProjectByProjectRequest(projectRequest);


//        String desiredFileName = projectRequest.getFileName();
//        Long sourceFileRecordId = projectRequest.getSourceFileRecordId();
//        FileRecord fileRecord = FileRecordUtil.generateFileRecord(sourceFileRecordId);
//        project.setSourceFileRecord(fileRecord);
//        updateDesiredFileNameIfDesiredFileNameNotNull(fileRecord, desiredFileName);

        createProjectCascadeEntities(projectRequest, project);

        Long projectId = project.getProjectId();

        shareProject2Organization(projectId, projectRequest.getOrganizationId());

        shareProject2UserWithPermission(projectRequest.getOperatorId(), projectId, Access.Permission.Creator);

        return project;
    }

    private void createProjectCascadeEntities(ProjectRequest projectRequest, Project project) {
        List<Task> tasks = new ArrayList<>();
        List<Remark> remarks = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();
        generateCreateSegmentsTasksAndRemarks(projectRequest, project, tasks, remarks, segments);

        segmentRepository.saveAll(segments);
        taskRepository.saveAll(tasks);
        remarkRepository.saveAll(remarks);
    }

    private Project createProjectByProjectRequest(ProjectRequest projectRequest) {
        Project project = new Project();


        project.setName(projectRequest.getName());

        project.setIsPublic(projectRequest.getIsPublic());

        project.setType(projectRequest.getType());

//        String desiredFileName = projectRequest.getFileName();
//        Long sourceFileRecordId = projectRequest.getSourceFileRecordId();
//        FileRecord fileRecord = FileRecordUtil.generateFileRecord(sourceFileRecordId);
//        project.setSourceFileRecord(fileRecord);
//        updateDesiredFileNameIfDesiredFileNameNotNull(fileRecord, desiredFileName);

        projectRepository.save(project);
        return project;
    }

    private void generateCreateSegmentsTasksAndRemarks(ProjectRequest projectRequest, Project project, List<Task> tasks, List<Remark> remarks, List<Segment> segments) {
        generateCreateSubSegmentsBySegmentRequests(projectRequest, project, tasks, remarks, segments);
        generateCreateGlobalSegmentByProjectRequest(projectRequest, project, tasks, remarks, segments);
    }

    private void generateCreateSubSegmentsBySegmentRequests(ProjectRequest projectRequest, Project project, List<Task> tasks, List<Remark> remarks, List<Segment> segments) {
        Long createBy = projectRequest.getOperatorId();
        List<ProjectRequest.SegmentRequest> segmentRequests = projectRequest.getSegmentRequests();
        segmentRequests.forEach(segmentRequest ->
                generateCreateSubSegmentBySegmentRequest(project, tasks, remarks, segments, createBy, segmentRequest));
    }

    private void generateCreateGlobalSegmentByProjectRequest(ProjectRequest projectRequest, Project project, List<Task> tasks, List<Remark> remarks, List<Segment> segments) {
        Long createBy = projectRequest.getOperatorId();
        Project.Type type = projectRequest.getType();
        Segment globalSegment = new Segment();

        ProjectRequest.RemarkRequest remarkRequest = projectRequest.getRemarkRequest();

        globalSegment.setProject(project);

        globalSegment.setIsGlobal(true);

        Remark remark = generateRemark(globalSegment, remarkRequest, createBy);
        remarks.add(remark);

        List<Task> newTasks = generateTasksForGlobalSegmentByProjectType(globalSegment, type);
        newTasks.forEach(task -> {
            if (task.getType() == Task.Workflow.SOURCE) {
                String desiredFileName = projectRequest.getFileName();
                Long sourceFileRecordId = projectRequest.getSourceFileRecordId();
                FileRecord fileRecord = FileRecordUtil.generateFileRecord(sourceFileRecordId);
                task.setFile(fileRecord);
                updateDesiredFileNameIfDesiredFileNameNotNull(fileRecord, desiredFileName);
            }
        });
        tasks.addAll(newTasks);

        segments.add(globalSegment);
    }

    private void generateCreateSubSegmentBySegmentRequest(Project project, List<Task> tasks, List<Remark> remarks, List<Segment> segments, Long createBy, ProjectRequest.SegmentRequest segmentRequest) {
        Segment segment = new Segment();

        segment.setProject(project);

        segment.setSummary(segmentRequest.getSummary());

        segment.setScope(segmentRequest.getScope());

        segment.setIsGlobal(false);

        var remark = generateRemark(segment, segmentRequest.getRemarkRequest(), createBy);
        remarks.add(remark);

        List<Task> newTasks = segmentRequest.getWorkflows().stream().map(workflow -> generateTask(segment, workflow)).toList();
        tasks.addAll(newTasks);

        segments.add(segment);
    }

    private void shareProject2UserWithPermission(Long createBy, Long projectId, Access.Permission permission) {
        Access access = new Access();
        Project p2 = new Project();
        p2.setProjectId(projectId);
        access.setProject(p2);
        User user = new User();
        user.setUserId(createBy);
        access.setUser(user);
        access.setCommitment(Access.Commitment.NONE);
        access.setPermission(permission);
        accessRepository.save(access);
    }

    private void shareProject2Organization(Long projectId, Long organizationId) {
        Organization organization = new Organization();
        organization.setOrganizationId(organizationId);
        Ownership ownership = new Ownership();

        Project project1 = new Project();
        project1.setProjectId(projectId);

        ownership.setProject(project1);
        ownership.setOrganization(organization);
        ownershipRepository.save(ownership);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Project updateProject(ProjectRequest projectRequest) {
        Long projectId = projectRequest.getProjectId();
        Project existingProject = getProjectById(projectId);
        String desiredFileName = projectRequest.getFileName();

//        FileRecord fileRecord = FileRecordUtil.generateFileRecord(projectRequest.getSourceFileRecordId());
//        existingProject.setSourceFileRecord(fileRecord);

        existingProject.setName(projectRequest.getName());

        existingProject.setIsPublic(projectRequest.getIsPublic());

        boolean isTypeUpdate = existingProject.getType() != projectRequest.getType();
        existingProject.setType(projectRequest.getType());


        List<Segment> updateSegments = new ArrayList<>();
        List<Segment> deleteSegments = new ArrayList<>();
        List<Segment> createSegments = new ArrayList<>();
        List<Task> deleteTasks = new ArrayList<>();
        List<Task> createTasks = new ArrayList<>();
        List<Remark> updateRemarks = new ArrayList<>();
        List<Remark> createRemarks = new ArrayList<>();
        Long createBy = projectRequest.getOperatorId();
        List<Segment> existingSegments = existingProject.getSegments();
        Map<Long, ProjectRequest.SegmentRequest> segmentRequestMap =
                projectRequest.getSegmentRequests().stream()
                        .filter(segmentRequest -> segmentRequest.getSegmentId() != null).collect(Collectors.toMap(ProjectRequest.SegmentRequest::getSegmentId, Function.identity()));

        existingSegments.forEach(existingSegment -> {
            Remark existingRemark = existingSegment.getRemarks().get(0);
            if (existingSegment.getIsGlobal()) {
                ProjectRequest.RemarkRequest remarkRequest = projectRequest.getRemarkRequest();

                if (!Objects.equals(remarkRequest.getContent(), existingRemark.getContent())) {
                    existingRemark.setContent(remarkRequest.getContent());
                    existingRemark.setUser(userRepository.getReferenceById(createBy));
                    updateRemarks.add(existingRemark);
                }

                if (isTypeUpdate) {
                    List<Task> newTasks = generateTasksForGlobalSegmentByProjectType(existingSegment, projectRequest.getType());
                    deleteTasks.addAll(existingSegment.getTasks());
                    createTasks.addAll(newTasks);
                }
                FileRecord fileRecord = fileRecordRepository.getReferenceById(projectRequest.getSourceFileRecordId());
                updateDesiredFileNameIfDesiredFileNameNotNull(fileRecord, desiredFileName);
                existingSegment.getTasks().forEach(task -> {
                    if (task.getType() == Task.Workflow.SOURCE) {
                        task.setFile(fileRecord);
                        taskRepository.save(task);
                    }
                });

            } else if (!segmentRequestMap.containsKey(existingSegment.getSegmentId())) {
                deleteSegments.add(existingSegment);
            } else {

                ProjectRequest.SegmentRequest segmentRequest = segmentRequestMap.get(existingSegment.getSegmentId());
                ProjectRequest.RemarkRequest remarkRequest = segmentRequest.getRemarkRequest();

                existingSegment.setSummary(segmentRequest.getSummary());

                existingSegment.setScope(segmentRequest.getScope());

                if (!Objects.equals(remarkRequest.getContent(), existingRemark.getContent())) {
                    existingRemark.setContent(remarkRequest.getContent());
                    existingRemark.setUser(userRepository.getReferenceById(createBy));
                    updateRemarks.add(existingRemark);
                }
                Set<Task.Workflow> requestWorkflowSet = new HashSet<>(segmentRequest.getWorkflows());

                existingSegment.getTasks().forEach(existingTask -> {
                    if (!requestWorkflowSet.contains(existingTask.getType())) {
                        deleteTasks.add(existingTask);
                    }
                });

                updateSegments.add(existingSegment);
            }
        });

        projectRequest.getSegmentRequests().stream()
                .filter(segmentRequest -> segmentRequest.getSegmentId() == null).forEach(createSegmentRequests -> {
                    generateCreateSubSegmentBySegmentRequest(existingProject, createTasks, createRemarks, createSegments, createBy, createSegmentRequests);
                });

        createSegments.addAll(updateSegments);
        segmentRepository.saveAll(createSegments);
        segmentRepository.deleteAll(deleteSegments);

        updateRemarks.addAll(createRemarks);
        remarkRepository.saveAll(updateRemarks);

        taskRepository.saveAll(createTasks);
        taskRepository.deleteAllInBatch(deleteTasks);

        existingProject.setSegments(null);
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
    public List<ProjectInfo> getAllPublicProjects() {
        return ProjectInfo.generateProjectInfos(projectRepository.findAllByIsPublic(true));
    }

    @Override
    public Project updateCover(Long projectId, Long coverId) {
        Project project = getProjectById(projectId);
        FileRecord fileRecord = FileRecordUtil.generateFileRecord(coverId);
        project.setCoverFileRecord(fileRecord);
        return projectRepository.save(project);
    }

    @Override
    public void shareProject2User(Long projectId, Long userId) {
        shareProject2UserWithPermission(userId, projectId, Access.Permission.Editable);
    }


    private void updateDesiredFileNameIfDesiredFileNameNotNull(FileRecord sourceFileRecord, String desiredFileName) {
        if (desiredFileName != null) {
            updateDesiredFileName(sourceFileRecord, desiredFileName);
        }
    }

    private void updateDesiredFileName(FileRecord fileRecord, String desiredFileName) {
        String suffix = fileRecord.getSuffix();
        fileRecord.setOriginalName(desiredFileName + "." + suffix);
        fileRecordRepository.save(fileRecord);
    }


    @Autowired
    SegmentRepository segmentRepository;

    @Autowired
    private TaskRepository taskRepository;


    private static Task generateTask(Segment segment, Task.Workflow workflow) {
        Task task = new Task();
        task.setStatus(Task.Status.NOT_ASSIGNED);
        task.setType(workflow);
        task.setSegment(segment);
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

    private Remark generateRemark(Segment segment, ProjectRequest.RemarkRequest remarkRequest, Long createBy) {
        User user = new User();
        Remark remark = new Remark();

        user.setUserId(createBy);

        remark.setSegment(segment);

        remark.setUser(user);

        remark.setContent(remarkRequest.getContent());

        return remark;
    }
}

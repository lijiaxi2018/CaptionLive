package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.ProjectCreateRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

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
    public Project createProject(ProjectCreateRequest projectCreateRequest) {
        Project project = new Project();
        project.setName(projectCreateRequest.getName());
        project.setIsPublic(projectCreateRequest.getIsPublic());
        project.setType(projectCreateRequest.getType());
        List<Segment> subSegments = new ArrayList<>(projectCreateRequest.getSegmentCreateRequests().stream().map(
                segmentCreateRequest -> {
                    Segment segment = new Segment();
                    segment.setProject(project);
                    segment.setSummary(segmentCreateRequest.getSummary());
                    segment.setBeginTime(segmentCreateRequest.getBeginTime());
                    segment.setEndTime(segmentCreateRequest.getEndTime());
                    ProjectCreateRequest.RemarkCreateRequest remarkCreateRequest = segmentCreateRequest.getRemarkCreateRequest();
                    User user = new User();
                    user.setUserId(remarkCreateRequest.getUserId());
                    Remark remark = new Remark();
                    remark.setUser(user);
                    segment.setRemarks(new ArrayList<>(List.of(remark)));
                    segment.setIsGlobal(false);
                    segment.setTasks(segmentCreateRequest.getWorkflows().stream().map(workflow -> {
                        Task task = new Task();
                        task.setStatus(Task.Status.NOT_ASSIGNED);
                        task.setType(workflow);
                        task.setSegment(segment);
                        return task;
                    }).toList());
                    return segment;
                }
        ).toList());
        Segment globalSegment = new Segment();
        globalSegment.setIsGlobal(true);
        ProjectCreateRequest.RemarkCreateRequest remarkCreateRequest = projectCreateRequest.getRemarkCreateRequest();
        User user = new User();
        user.setUserId(remarkCreateRequest.getUserId());
        Remark remark = new Remark();
        remark.setUser(user);
        globalSegment.setRemarks(new ArrayList<>(List.of(remark)));
        globalSegment.setTasks(generateTasksForGlobalSegmentByProjectType(globalSegment, projectCreateRequest.getType()));
        ArrayList<Segment> segments = new ArrayList<>(subSegments);
        segments.add(globalSegment);
        project.setSegments(segments);
        return projectRepository.save(project);
    }


    private List<Task> generateTasksForGlobalSegmentByProjectType(Segment globalSegment, Project.Type type) {
        switch (type) {
            case TXT -> {
                return Arrays.stream(new Task.Workflow[]{Task.Workflow.SOURCE, Task.Workflow.F_CHECK}).map(t -> Task.createTaskBySegmentAndWorkFlow(globalSegment, t)).toList();
            }
            case AUDIO_AND_VIDEO -> {
                return Arrays.stream(new Task.Workflow[]{Task.Workflow.SOURCE, Task.Workflow.F_CHECK, Task.Workflow.RENDERING}).map(t -> Task.createTaskBySegmentAndWorkFlow(globalSegment, t)).toList();
            }
            default -> {
                return null;
            }
        }
    }


    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        project.setProjectId(id);
        return projectRepository.save(project);
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

}

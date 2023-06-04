package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.OrganizationRequest;
import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.repository.FileRecordRepository;
import com.aguri.captionlive.repository.OrganizationRepository;
import com.aguri.captionlive.repository.ProjectRepository;
import com.aguri.captionlive.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization createOrganization(OrganizationRequest organizationRequest) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationRequest, organization);
        organization.setAvatar(new FileRecord(organizationRequest.getAvatarId()));
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Long id, OrganizationRequest organizationRequest) {
        Organization existingOrganization = getOrganizationById(id);
        existingOrganization.setName(organizationRequest.getName());
        existingOrganization.setDescription(organizationRequest.getDescription());
        existingOrganization.setAvatar(new FileRecord(organizationRequest.getAvatarId()));
        return organizationRepository.save(existingOrganization);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization = getOrganizationById(id);
        organizationRepository.delete(organization);
    }

    @Override
    public List<ProjectInfo> getPagedProjects(Long organizationId, String searchTxt, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Project> projectsPage = projectRepository.findAllByOrganizationsOrganizationIdAndNameContaining(organizationId, searchTxt, pageable);
        List<Project> projects = projectsPage.getContent();
        // fields copying...
        return projects.stream().map(project -> {
            ProjectInfo projectInfo = new ProjectInfo();
            projectInfo.setTitle(project.getName());
            List<Segment> segments = project.getSegments();
            projectInfo.setSegmentInfos(new ArrayList<>());
            segments.forEach(segment -> {
                ProjectInfo.SegmentInfo segmentInfo = new ProjectInfo.SegmentInfo();
                segmentInfo.setSummary(segment.getSummary());
                segmentInfo.setBeginTime(segment.getBeginTime());
                segmentInfo.setEndTime(segment.getEndTime());
                segmentInfo.setRemarks(segment.getRemarks());
                List<ProjectInfo.SegmentInfo.TaskInfo> taskInfos = segment.getTasks().stream().map(task -> {
                    ProjectInfo.SegmentInfo.TaskInfo taskInfo = new ProjectInfo.SegmentInfo.TaskInfo();
                    taskInfo.setWorkerUser(task.getWorker());
                    taskInfo.setStatus(task.getStatus());
                    taskInfo.setHasUploadedFile(task.getFile() != null);
                    return taskInfo;
                }).toList();
                segmentInfo.setTaskInfos(taskInfos);
                if (segment.getIsGlobal()) {
                    projectInfo.setIsCompleted(segment.getEndTime() != null);
                    projectInfo.setOverview(segmentInfo);
                } else {
                    projectInfo.getSegmentInfos().add(segmentInfo);
                }
            });
            return projectInfo;
        }).toList();
    }
}

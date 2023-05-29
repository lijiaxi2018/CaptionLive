package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.repository.OrganizationRepository;
import com.aguri.captionlive.repository.ProjectRepository;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Long id, Organization organization) {
        Organization existingOrganization = getOrganizationById(id);
        existingOrganization.setName(organization.getName());
        existingOrganization.setDescription(organization.getDescription());
        existingOrganization.setAvatar(organization.getAvatar());
        return organizationRepository.save(existingOrganization);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization = getOrganizationById(id);
        organizationRepository.delete(organization);
    }

    @Autowired
    FileRecordService fileRecordService;

    @Override
    public Organization saveAvatarToStorage(Long id, MultipartFile file) {
        Organization organization = getOrganizationById(id);
        if (!file.isEmpty()) {
            try {
                FileRecord fileRecord = fileRecordService.saveSmallSizeFile(file, "avatar" + File.separator + "organization" + File.separator + id.toString());
                organization.setAvatar(fileRecord.getFileRecordId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updateOrganization(id, organization);
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
                List<ProjectInfo.SegmentInfo.TaskInfo> taskInfos = segment.getTasks().stream().map(task -> {
                    ProjectInfo.SegmentInfo.TaskInfo taskInfo = new ProjectInfo.SegmentInfo.TaskInfo();
                    taskInfo.setWorkerName(task.getWorker().getUsername());
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

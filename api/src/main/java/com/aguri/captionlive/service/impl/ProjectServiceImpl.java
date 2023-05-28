package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnershipRepository ownershipRepository;

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
    public Project createProject(Project project) {
        return projectRepository.save(project);
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

    @Autowired
    FileRecordService fileRecordService;

    @Override
    public Project uploadAvatar(Long projectId, MultipartFile file) {
        Project project = getProjectById(projectId);
        if (!file.isEmpty()) {
            try {
                FileRecord fileRecord = fileRecordService.saveSmallSizeFile(file, "cover" + File.separator + "project" + File.separator + projectId.toString());
                project.setCoverFileRecord(fileRecord);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updateProject(projectId, project);
    }
}

package com.aguri.captionlive.service;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.DTO.ProjectRequest;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project createProject(ProjectRequest projectRequest);
    Project createProject(Project newProject);

    Project getProjectById(Long id);

    void deleteProject(Long id);

    Project updateProject(ProjectRequest projectRequest);

    List<User> getAllAccessibleUsers(Long projectId);

    List<Organization> getAllAccessibleOrganizations(Long projectId);

    List<ProjectInfo> getAllPublicProjects();

    Project updateCover(Long projectId, Long coverId);

    void shareProject2User(Long projectId, Long userId);

    void shareProject2Organization(Long projectId, Long organizationId);

}
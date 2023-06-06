package com.aguri.captionlive.service;

import com.aguri.captionlive.DTO.ProjectRequest;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project createProject(ProjectRequest projectRequest);

    Project getProjectById(Long id);

    void deleteProject(Long id);

    Project updateProject(ProjectRequest projectRequest);

    List<User> getAllAccessibleUsers(Long projectId);

    List<Organization> getAllAccessibleOrganizations(Long projectId);

    List<Project> getAllPublicProjects();
}
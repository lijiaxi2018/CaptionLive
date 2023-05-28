package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project createProject(Project project);

    Project getProjectById(Long id);

    void deleteProject(Long id);

    Project updateProject(Long id, Project project);

    List<User> getAllAccessibleUsers(Long projectId);

    List<Organization> getAllAccessibleOrganizations(Long projectId);

    List<Project> getAllPublicProjects();

    Project uploadAvatar(Long projectId, MultipartFile file);
}
package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project createProject(Project project);

    Project getProjectById(Long id);

    void deleteProject(Long id);

    Project updateProject(Long id, Project project);
}
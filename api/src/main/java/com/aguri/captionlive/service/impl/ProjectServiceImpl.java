package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.repository.ProjectRepository;
import com.aguri.captionlive.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

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
}

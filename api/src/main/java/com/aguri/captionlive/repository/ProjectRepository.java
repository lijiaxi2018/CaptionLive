package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByIsPublic(boolean isPublic);
    List<Project> findAllByOrganizationsOrganizationIdAndNameContains(Long organizations_organizationId, String name);

}

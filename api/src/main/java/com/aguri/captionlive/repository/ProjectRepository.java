package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Segment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findAllByIsPublic(boolean isPublic);

    Page<Project> findAllByOrganizationsOrganizationIdAndNameContaining(Long organizationId, String searchTxt, Pageable pageable);
}

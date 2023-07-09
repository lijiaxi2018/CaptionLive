package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findAllByProjectsProjectId(Long projectId);

    Page<Organization> findAllByNameContaining(String searchTxt, Pageable pageable);
}

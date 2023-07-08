package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findAllByProjectProjectId(Long projectId);
}

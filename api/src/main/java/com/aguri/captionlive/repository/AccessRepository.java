package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findAllByProjectId(Long projectId);
}

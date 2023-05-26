package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}

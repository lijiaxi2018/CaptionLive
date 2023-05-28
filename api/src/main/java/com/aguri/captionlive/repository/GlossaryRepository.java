package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Glossary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GlossaryRepository extends JpaRepository<Glossary, Long> {
    List<Glossary> findByOrganizationOrganizationId(Long organizationId);
}

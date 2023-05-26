package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
}

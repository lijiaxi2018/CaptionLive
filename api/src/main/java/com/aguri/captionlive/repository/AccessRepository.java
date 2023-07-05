package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {

    List<Access> findAllByUserUserIdAndCommitment(Long userId, Access.Commitment commitment);

    List<Access> findAllByProjectProjectId(Long project_projectId);

    Access findAccessByProjectProjectIdAndUserUserId(Long project_projectId, Long user_userId);

    List<Access> findAccessByProjectProjectId(Long projectId);

    List<Long> findExistingUserIdsByProjectProjectId(Long projectId);
}

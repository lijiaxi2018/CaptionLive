package com.aguri.captionlive.repository;
import com.aguri.captionlive.model.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findByRequestId(Long requestId);
    List<Request> findAllBySender(Long UserId);
    List<Request> findAllByRecipient(Long UserId);
}


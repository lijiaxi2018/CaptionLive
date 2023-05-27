package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Additional query methods if needed
    // For example: List<Message> findByRequestId(Long requestId);
}

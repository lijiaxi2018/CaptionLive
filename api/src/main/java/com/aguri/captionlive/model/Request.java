package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private Long type;

    private int status;

    private Long sender;

    private Long recipient;

    private boolean senderRead;

    private boolean recipientRead;

    private String body;

    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;
    
}

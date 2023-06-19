package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
    @JoinColumn(name = "request_id")
    private Request request;

    private Boolean isReply;

    private String Content;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;
    
}

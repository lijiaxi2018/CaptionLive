package com.aguri.captionlive.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "errors")
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorId;

    @Column(columnDefinition = "TEXT")
    private String body;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;
}

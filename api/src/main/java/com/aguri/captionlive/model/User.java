package com.aguri.captionlive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Integer permission;

    private String username;

    private String password;

    private String qq;

    private Long avatar;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdTime;

}

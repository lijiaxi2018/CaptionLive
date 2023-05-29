package com.aguri.captionlive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private Integer permission;

    @Column(unique = true)
    private String username;

    private String password;

    private String qq;

    private String email;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "avatar_file_record_id")
    private FileRecord avatar;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "memberships",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Organization> organizations;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "accesses",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Project> accessibleProjects;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdTime;

}

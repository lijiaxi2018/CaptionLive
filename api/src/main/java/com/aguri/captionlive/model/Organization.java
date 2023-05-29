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
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    private String name;

    private String description;

    private Long avatar;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "memberships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private List<User> users;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ownerships",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private List<Project> projects;


    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdTime;

}

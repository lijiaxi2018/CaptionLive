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
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String name;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cover_file_record_id")
    private FileRecord coverFileRecord;

    private Integer type;

    private Boolean isPublic;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ownerships",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Organization> organizations;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<Segment> segments;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "accesses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<User> accessibleUsers;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdTime;

}

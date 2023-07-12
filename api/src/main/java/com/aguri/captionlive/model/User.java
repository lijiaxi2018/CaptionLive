package com.aguri.captionlive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "idx_username", columnList = "username")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private Integer permission;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String qq;

    private String email;

    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "avatar_file_record_id")
    private FileRecord avatar;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "memberships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private List<Organization> organizations;


    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "accesses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> accessibleProjects;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Transient
    private Long avatarId;

    public Long getAvatarId() {
        if (avatar == null) return 0L;
        return avatar.getFileRecordId();
    }

}

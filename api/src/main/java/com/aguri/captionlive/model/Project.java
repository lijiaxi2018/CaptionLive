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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cover_file_record_id")
    private FileRecord coverFileRecord;

    private Type type;

    private Boolean isPublic;

    @ManyToMany
    @JoinTable(
            name = "ownerships",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Organization> organizations;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Segment> segments;

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

    public enum Type {
        AUDIO_AND_VIDEO(0),
        TXT(1);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}

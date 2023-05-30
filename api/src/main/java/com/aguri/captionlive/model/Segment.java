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
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long segmentId;

    private Boolean isGlobal;

    private String summary;

    private Integer beginTime;

    private Integer endTime;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "segment", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}

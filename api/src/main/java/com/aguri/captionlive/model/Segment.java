package com.aguri.captionlive.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long segmentId;

    private Boolean isGlobal;

    private String summary;

    private String scope;

    private Integer displayOrder;

    @OneToMany(mappedBy = "segment", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "segment", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Remark> remarks;


    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;
}

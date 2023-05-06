package com.example.ll.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "segments")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long segmentId;

    private Long projectId;

    private Boolean Global;

    private Integer workflow;

    private String summary;

//    @Temporal(TemporalType.TIMESTAMP)
    private Integer beginTime;

//    @Temporal(TemporalType.TIMESTAMP)
    private Integer endTime;

}

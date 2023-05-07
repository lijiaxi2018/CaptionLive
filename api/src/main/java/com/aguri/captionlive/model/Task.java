package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private Long segmentId;

    private Integer type;

    private Integer status;

    private Long Worker;

    private Timestamp acceptedTime;

    private Long FileId;

}

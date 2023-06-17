package com.aguri.captionlive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private Workflow type;

    private Status status;

    private LocalDate acceptedTime;

    private Integer displayOrder;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "segment_id")
    private Segment segment;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_user_id")
    private User worker;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "file_record_id")
    private FileRecord file;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;

    public static final Workflow[] TXT_DEFAULT_WORKFLOWS = new Workflow[]{Workflow.SOURCE, Workflow.F_CHECK};
    public static final Workflow[] AUDIO_AND_VIDEO_DEFAULT_WORKFLOWS = new Workflow[]{Workflow.SOURCE, Workflow.F_CHECK, Workflow.RENDERING};

    public enum Workflow {
        TIMELINE(0),
        K_TIMELINE(1),
        S_TIMELINE(2),
        TRANSLATION(3),
        EFFECT(4),
        POLISHING(5),
        EMBEDDING(6),
        CHECK(7),
        SOURCE(8),
        F_CHECK(9),
        RENDERING(10);

        private final int value;

        Workflow(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Status {
        NOT_ASSIGNED(0),
        IN_PROGRESS(1),
        COMPLETED(2);

        private final int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static Task createTaskBySegmentAndWorkFlow(Segment segment, Task.Workflow workflow) {
        Task task = new Task();
        task.setStatus(Task.Status.NOT_ASSIGNED);
        task.setType(workflow);
        task.setSegment(segment);
        return task;
    }
}

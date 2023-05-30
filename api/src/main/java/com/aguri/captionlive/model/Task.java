package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "segment_id")
    private Segment segment;

    private Workflow type;

    private Status status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_user_id")
    private User worker;

    private LocalDate acceptedTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_record_id")
    private FileRecord file;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;

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

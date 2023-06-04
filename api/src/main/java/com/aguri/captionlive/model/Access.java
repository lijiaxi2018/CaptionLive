package com.aguri.captionlive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "accesses")
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Permission permission;

    @Enumerated(EnumType.ORDINAL)
    private Commitment commitment;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @CreationTimestamp
    private LocalDateTime createdTime;


    public enum Commitment {
        NONE(0),
        COMMITTED(1);

        private final int value;

        Commitment(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public enum Permission {
        Creator(0),
        Editable(1);

        private final int value;

        Permission(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}

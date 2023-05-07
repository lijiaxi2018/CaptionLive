package com.aguri.captionlive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    private Long userId;

    private Long organizationId;

    private Integer permission;

    private Integer position;

}

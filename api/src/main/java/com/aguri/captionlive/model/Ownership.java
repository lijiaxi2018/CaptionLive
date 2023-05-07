package com.aguri.captionlive.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ownerships")
public class Ownership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownershipId;

    private Long projectId;

    private Long organizationId;

}

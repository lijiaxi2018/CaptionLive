package com.aguri.captionlive.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    private String name;

    private String description;

    private String avatar;

}

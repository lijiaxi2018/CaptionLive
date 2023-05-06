package com.example.ll.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class MemberShip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberShipId;

    private Long userId;

    private Long organizationId;

    private Integer permission;

    private Integer position;

}

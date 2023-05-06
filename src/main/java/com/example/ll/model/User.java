package com.example.ll.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Integer permission;

    private String username;

    private String password;

    private String qq;

    private String avatar;

    private String email;

}

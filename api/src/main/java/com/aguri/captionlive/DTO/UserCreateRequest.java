package com.aguri.captionlive.DTO;

import lombok.Data;

@Data
public class UserCreateRequest {

    private Integer permission;

    private String username;

    private String password;

    private String qq;

    private String email;

    private Long avatarId;
}

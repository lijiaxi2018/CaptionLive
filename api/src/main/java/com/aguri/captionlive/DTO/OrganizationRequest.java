package com.aguri.captionlive.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizationRequest {
    private Long organizationId;

    private String name;

    private String description;

    private Long avatarId;

}

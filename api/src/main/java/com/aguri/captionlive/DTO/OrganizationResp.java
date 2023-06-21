package com.aguri.captionlive.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrganizationResp {

    private Long organizationId;

    private String name;

    private String description;

    private Long avatarId;

    private List<Long> LeaderIds;

    private LocalDateTime lastUpdatedTime;

    private LocalDateTime createdTime;

}

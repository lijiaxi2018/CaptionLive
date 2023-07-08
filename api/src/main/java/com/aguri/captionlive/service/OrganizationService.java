package com.aguri.captionlive.service;

import com.aguri.captionlive.DTO.OrganizationRequest;
import com.aguri.captionlive.DTO.OrganizationResp;
import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.User;

import java.util.List;


public interface OrganizationService {

    Organization getOrganizationById(Long id);

    List<Organization> getAllOrganizations();

    Organization createOrganization(OrganizationRequest organizationRequest, User user);

    Organization updateOrganization(Long id, OrganizationRequest organizationRequest);

    void deleteOrganization(Long id);

    List<ProjectInfo> getPagedProjects(Long organizationId, String searchTxt, int page, int size, String sortBy, String sortOrder);

    Organization updateDescription(Long organizationId, String description);

    Organization updateAvatar(Long organizationId, Long avatarId);

    OrganizationResp getResp(Organization organization);
}

package com.aguri.captionlive.service;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationService {

    Organization getOrganizationById(Long id);

    List<Organization> getAllOrganizations();

    Organization createOrganization(Organization organization);

    Organization updateOrganization(Long id, Organization organization);

    void deleteOrganization(Long id);

    Organization saveAvatarToStorage(Long id, MultipartFile file);

     List<ProjectInfo> getPagedProjects(Long organizationId, String searchTxt, int page, int size, String sortBy, String sortOrder);
}

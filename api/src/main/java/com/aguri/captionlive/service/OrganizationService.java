package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Organization;

import java.util.List;

public interface OrganizationService {

    Organization getOrganizationById(Long id);

    List<Organization> getAllOrganizations();

    Organization createOrganization(Organization organization);

    Organization updateOrganization(Long id, Organization organization);

    void deleteOrganization(Long id);
}

package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Ownership;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.repository.OwnershipRepository;
import com.aguri.captionlive.service.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class OwnershipServiceImpl implements OwnershipService {


    @Autowired
    OwnershipRepository ownershipRepository;

    @Override
    public void shareProject2Organization(Long projectId, Long organizationId) {
        Organization organization = new Organization();
        organization.setOrganizationId(organizationId);
        Ownership ownership = new Ownership();
        ownership.setOrganization(organization);
        Project project1 = new Project();
        project1.setProjectId(projectId);
        ownership.setProject(project1);
        ownershipRepository.save(ownership);
    }
}

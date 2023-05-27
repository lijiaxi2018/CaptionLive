package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Ownership;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.repository.OrganizationRepository;
import com.aguri.captionlive.repository.OwnershipRepository;
import com.aguri.captionlive.repository.ProjectRepository;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OwnershipRepository ownershipRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Long id, Organization organization) {
        Organization existingOrganization = getOrganizationById(id);
        existingOrganization.setName(organization.getName());
        existingOrganization.setDescription(organization.getDescription());
        existingOrganization.setAvatar(organization.getAvatar());
        return organizationRepository.save(existingOrganization);
    }

    @Override
    public void deleteOrganization(Long id) {
        Organization organization = getOrganizationById(id);
        organizationRepository.delete(organization);
    }

    @Autowired
    FileRecordService fileRecordService;

    @Override
    public Organization saveAvatarToStorage(Long id, MultipartFile file) {
        Organization organization = getOrganizationById(id);
        if (!file.isEmpty()) {
            try {
                FileRecord fileRecord = fileRecordService.saveFile(file, "avatar" + File.separator + "organization" + File.separator + id.toString());
                organization.setAvatar(fileRecord.getFileRecordId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updateOrganization(id, organization);
    }

    @Override
    public List<Project> getAllProjects(Long organizationId) {
        List<Ownership> organizations = ownershipRepository.findAllByOrganizationId(organizationId);
        List<Long> projectIds = organizations.stream().map(Ownership::getProjectId).toList();
        return projectRepository.findAllById(projectIds);
    }
}

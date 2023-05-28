package com.aguri.captionlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Glossary;
import com.aguri.captionlive.repository.GlossaryRepository;
import com.aguri.captionlive.service.GlossaryService;

import java.util.List;

@Service
public class GlossaryServiceImpl implements GlossaryService {

    private final GlossaryRepository glossaryRepository;

    @Autowired
    public GlossaryServiceImpl(GlossaryRepository glossaryRepository) {
        this.glossaryRepository = glossaryRepository;
    }

    @Override
    public List<Glossary> getAllGlossarys() {
        return glossaryRepository.findAll();
    }

    @Override
    public Glossary getGlossaryById(Long id) {
        return glossaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Glossary not found with id: " + id));
    }

    @Override
    public Glossary createGlossary(Glossary glossary) {
        return glossaryRepository.save(glossary);
    }

    @Override
    public Glossary updateGlossary(Long id, Glossary glossary) {
        Glossary existingGlossary = getGlossaryById(id);
        //Need to confirm what attributes should be update
        existingGlossary.setCategory(glossary.getCategory());
        return glossaryRepository.save(existingGlossary);
    }

    @Override
    public void deleteGlossary(Long id) {
        glossaryRepository.deleteById(id);
    }

    @Override
    public List<Glossary> getAllGlossarysByOrganization(Long organizationId) {
        // Implement fetching glossary terms by organizationId
        return glossaryRepository.findByOrganizationOrganizationId(organizationId);
    }
}


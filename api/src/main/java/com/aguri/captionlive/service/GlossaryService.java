package com.aguri.captionlive.service;

import java.util.List;

import com.aguri.captionlive.model.Glossary;

public interface GlossaryService {

    List<Glossary> getAllGlossaries();

    Glossary getGlossaryById(Long id);

    Glossary createGlossary(Glossary request);

    Glossary updateGlossary(Long id, Glossary request);

    void deleteGlossary(Long id);

    List<Glossary> getAllGlossariesByOrganization(Long organizationId);
}


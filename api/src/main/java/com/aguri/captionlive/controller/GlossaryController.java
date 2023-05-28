package com.aguri.captionlive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Glossary;
import com.aguri.captionlive.service.GlossaryService;

@RestController
@RequestMapping("/api/glossaries")
public class GlossaryController {

    private final GlossaryService glossaryService;

    @Autowired
    public GlossaryController(GlossaryService glossaryService) {
        this.glossaryService = glossaryService;
    }

    @GetMapping
    public ResponseEntity<Resp> getAllGlossarys() {
        return ResponseEntity.ok(Resp.ok(glossaryService.getAllGlossarys()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getGlossaryById(@PathVariable Long id) {
        return ResponseEntity.ok(Resp.ok(glossaryService.getGlossaryById(id)));
    }

    @PostMapping
    public ResponseEntity<Resp> createGlossary(@RequestBody Glossary request) {
        return ResponseEntity.ok(Resp.ok(glossaryService.createGlossary(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateGlossary(@PathVariable Long id, @RequestBody Glossary request) {
        return ResponseEntity.ok(Resp.ok(glossaryService.updateGlossary(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteGlossary(@PathVariable Long id) {
        glossaryService.getGlossaryById(id);
        glossaryService.deleteGlossary(id);
        return ResponseEntity.noContent().build();
      
    }

    @GetMapping("/getAllGlossaries/{organizationId}")
    public ResponseEntity<Resp> getAllGlossarysByOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(Resp.ok(glossaryService.getAllGlossarysByOrganization(organizationId)));
    }
}

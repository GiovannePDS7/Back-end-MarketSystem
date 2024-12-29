package com.MarketSystem.controller;

import com.MarketSystem.models.Section;
import com.MarketSystem.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping
    public ResponseEntity<Section> createOrUpdateSection(@RequestBody Section section) {
        Section savedSection = sectionService.saveSection(section);
        return ResponseEntity.ok(savedSection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable String id, @RequestBody Section updatedSection) {
        return sectionService.getSectionById(id)
                .map(existingSection -> {
                    existingSection.setLocation(updatedSection.getLocation());  // Atualização do campo location
                    Section savedSection = sectionService.saveSection(existingSection);
                    return ResponseEntity.ok(savedSection);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable String id) {
        return sectionService.getSectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSectionById(@PathVariable String id) {
        if (sectionService.getSectionById(id).isPresent()) {
            sectionService.deleteSectionById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> sections = sectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }
}

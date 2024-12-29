package com.MarketSystem.services;

import com.MarketSystem.models.Section;
import com.MarketSystem.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public Optional<Section> getSectionById(String id) {
        return sectionRepository.findById(id);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public void deleteSectionById(String id) {
        sectionRepository.deleteById(id);
    }
}

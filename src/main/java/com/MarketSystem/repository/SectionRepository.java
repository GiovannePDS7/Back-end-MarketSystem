package com.MarketSystem.repository;

import com.MarketSystem.models.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SectionRepository extends MongoRepository<Section, String> {
}

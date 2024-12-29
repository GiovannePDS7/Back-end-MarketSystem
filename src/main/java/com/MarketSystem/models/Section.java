package com.MarketSystem.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Section {

    @Id
    private String id;
    private String location;
}

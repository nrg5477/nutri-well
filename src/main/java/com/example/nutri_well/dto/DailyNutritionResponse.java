package com.example.nutri_well.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DailyNutritionResponse {
    private Map<String, Double> nutrients = new HashMap<>();

    public void addNutrient(String name, double amount) {
        nutrients.put(name, nutrients.getOrDefault(name, 0.0) + amount);
    }
}

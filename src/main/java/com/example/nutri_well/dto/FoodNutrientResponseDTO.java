package com.example.nutri_well.dto;

import com.example.nutri_well.entity.FoodNutrient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodNutrientResponseDTO {
    private String name;
    private String value;
    private double amount;

    public static FoodNutrientResponseDTO of(FoodNutrient foodNutrient) {
        return new FoodNutrientResponseDTO(foodNutrient.getNutrient().getName(),
                foodNutrient.getNutrient().getServingUnit(),
                foodNutrient.getAmount());
    }
}
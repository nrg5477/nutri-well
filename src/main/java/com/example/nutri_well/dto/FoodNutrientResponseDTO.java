package com.example.nutri_well.dto;

import com.example.nutri_well.entity.FoodNutrient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//njg 순환참조를 방지하기위한 Response용 DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodNutrientResponseDTO {
    private String name;
    private String value;
    private double amount;

    // 엔티티를 DTO로 변환하는 메소드
    public static FoodNutrientResponseDTO of(FoodNutrient foodNutrient) {
        return new FoodNutrientResponseDTO(foodNutrient.getNutrient().getName(),
                foodNutrient.getNutrient().getServingUnit(),
                foodNutrient.getAmount());
    }
}

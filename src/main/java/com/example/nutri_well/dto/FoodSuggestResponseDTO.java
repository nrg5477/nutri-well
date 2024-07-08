package com.example.nutri_well.dto;

import com.example.nutri_well.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodSuggestResponseDTO {
    String name;

    public static FoodSuggestResponseDTO of(Food food) {
        return new FoodSuggestResponseDTO(food.getName());
    }
}
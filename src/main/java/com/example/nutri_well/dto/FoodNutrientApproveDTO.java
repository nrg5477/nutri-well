package com.example.nutri_well.dto;

import com.example.nutri_well.entity.FoodNutrientApprove;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodNutrientApproveDTO {
    private Long nutrientId;
    private String nutrientName;
    private double amount;

    //엔티티를 DTO 객체로 변환하는 메소드
    public static FoodNutrientApproveDTO of(FoodNutrientApprove foodNutrientApprove) {
        return new FoodNutrientApproveDTO(
                foodNutrientApprove.getNutrient().getId(),
                foodNutrientApprove.getNutrient().getName(),
                foodNutrientApprove.getAmount()
        );
    }
}
package com.example.nutri_well.dto;

import com.example.nutri_well.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodApproveRequestDTO {
    private String name;
    private Category categoryId;
    private String product;
    private String manufacturer;
//    private String servingSize;
    private int weight;
    private String userEmail;
    private List<FoodNutrientApproveDTO> nutrients;
}
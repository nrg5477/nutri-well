package com.example.nutri_well.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientResponseDTO {
    private Long id;
    private String name;
    private String servingUnit; // 단위
}
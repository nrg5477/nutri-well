package com.example.nutri_well.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPageWrapperDTO {
    private List<CategoryResponseDTO> categoryResponseDTO;
    private List<FoodResponseDTO> foodResponseDTO;
}

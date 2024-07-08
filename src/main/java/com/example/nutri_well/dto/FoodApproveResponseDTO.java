package com.example.nutri_well.dto;

import com.example.nutri_well.entity.FoodApprove;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodApproveResponseDTO {
    private Long id;
    private String name;
    private CategorySummaryDTO categoryId;
    private String product;
    private Date requestDate;
    private Date approvalDate;
    private String manufacturer;
    private String servingSize;
    private String userEmail;
    private boolean approved;

    @JsonManagedReference
    private List<FoodNutrientApproveDTO> nutrients;

    public static FoodApproveResponseDTO of(FoodApprove foodApprove) {
        return new FoodApproveResponseDTO(
                foodApprove.getId(),
                foodApprove.getName(),
                CategorySummaryDTO.of(foodApprove.getCategoryId()),
                foodApprove.getProduct(),
                foodApprove.getRequestDate(),
                foodApprove.getApprovalDate(),
                foodApprove.getManufacturer(),
                foodApprove.getServingSize(),
                foodApprove.getUser().getEmail(),
                foodApprove.isApproved(),
                foodApprove.getNutrientlist().stream()
                        .map(FoodNutrientApproveDTO::of)
                        .collect(Collectors.toList())
        );
    }
}

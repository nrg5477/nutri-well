package com.example.nutri_well.service;

import com.example.nutri_well.dto.FoodApproveRequestDTO;
import com.example.nutri_well.dto.FoodApproveResponseDTO;

import java.util.List;

public interface FoodApproveService {
    FoodApproveResponseDTO requestFoodApproval(FoodApproveRequestDTO requestDTO);

    FoodApproveResponseDTO approveFood(Long requestId);

    List<FoodApproveResponseDTO> getAllFoodApprovals();
}
package com.example.nutri_well.service;

import com.example.nutri_well.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> findAll();

    List<CategoryResponseDTO> findByParentCategoryIsNull();

    CategoryResponseDTO findbyId(Long id);

    List<CategoryResponseDTO> getAllCategories();
}

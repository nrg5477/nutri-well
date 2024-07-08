package com.example.nutri_well.service;

import com.example.nutri_well.dao.CategoryDAO;
import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO dao;

    @Override
    public List<CategoryResponseDTO> findByParentCategoryIsNull() {
        List<Category> categories = dao.findByparentCategoryIsNull();
        List<CategoryResponseDTO> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(CategoryResponseDTO.of(category));
        }
        return list;
    }

    @Override
    public CategoryResponseDTO findbyId(Long id) {
        Category category = dao.findbyId(id);
        return CategoryResponseDTO.of(category);
    }
}
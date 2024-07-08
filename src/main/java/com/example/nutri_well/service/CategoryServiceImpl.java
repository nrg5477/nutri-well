package com.example.nutri_well.service;

import com.example.nutri_well.dao.CategoryDAO;
import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.entity.Category;
import com.example.nutri_well.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO dao;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = dao.findAll();
        List<CategoryResponseDTO> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(CategoryResponseDTO.of(category));
        }
        return list;
    }

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

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }
}

package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll(); // 전체 리스트 찾기
    List<Category> findByparentCategoryIsNull(); // 대분류인 Category 찾기
    Category findbyId(Long id); // CategoryId 찾기
}
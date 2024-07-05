package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Category;
import com.example.nutri_well.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodDAO {
    Page<Food> searchByFoodName(String name, Pageable pageable);
    Page<Food> searchByCategoryId(Long categoryid, Pageable pageable);
    Page<Food> findByparentCategoryFood(Category parentCategory, Pageable pageable);
    int getTotalPages(Page<Food> page);
    Food findByName(String name);
    Page<Food> findAllByNutrientsNotIn(String foodname, List<String> names, Pageable pageable);
    Page<Food> findAllByNutrientsNotIn(Long categoryid, List<String> names, Pageable pageable);
    Food findById(Long foodId);
    Food findByFoodCode(String foodcode);
    Page<Food> findAllByNutrientsInRange( String foodname, List<String> names, Integer min, Integer max, Pageable pageable);
    Page<Food> findAllByNutrientsInRange(Long category,  List<String> names, Integer min,  Integer max, Pageable pageable);
    Page<Food> findAllByNutrientsParentCategoryInRange(Long category,  List<String> names, Integer min,  Integer max, Pageable pageable);
    Food save(Food food);
    List<Food> findByNameStartingWith(String prefix,Pageable pageable);
}

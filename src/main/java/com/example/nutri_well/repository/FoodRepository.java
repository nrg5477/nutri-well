package com.example.nutri_well.repository;

import com.example.nutri_well.entity.Category;
import com.example.nutri_well.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FoodRepository extends JpaRepository<Food,Long> {
    Food findByName(String name);
    Page<Food> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Query("SELECT f FROM Food f WHERE f.categoryId.id = :categoryId")
    Page<Food> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT f FROM Food f WHERE f.categoryId.parentCategory = :categoryId")
    Page<Food> findByparentCategoryFood(@Param("categoryId") Category parentCategory, Pageable pageable);

    //이름으로 검색했을때
    @Query("SELECT f FROM Food f " +
            "WHERE f.name LIKE :name AND " +
            "NOT EXISTS (SELECT 1 FROM f.nutrientlist fn WHERE fn.nutrient.name IN :names)")
    Page<Food> findAllByNutrientsNotIn(@Param("name")String foodname, @Param("names") List<String> names, Pageable pageable);
    //카테고리로 검색했을때
    @Query("SELECT f FROM Food f " +
            "WHERE f.categoryId.id = :categoryId AND " +
            "NOT EXISTS (SELECT 1 FROM f.nutrientlist fn WHERE fn.nutrient.name IN :names)")
    Page<Food> findAllByNutrientsNotIn(@Param("categoryId")Long category, @Param("names") List<String> names, Pageable pageable);
    //이름으로 했을때
    @Query("SELECT f FROM Food f " +
            "JOIN f.nutrientlist fn " +
            "WHERE f.name LIKE :name AND " +
            "fn.nutrient.name IN :names AND " +
            "fn.amount BETWEEN :min AND :max")
    Page<Food> findAllByNutrientsInRange(@Param("name") String foodname, @Param("names") List<String> names,
                                         @Param("min") Integer min, @Param("max") Integer max, Pageable pageable);
    //카테고리로 했을때
    @Query("SELECT f FROM Food f " +
            "JOIN f.nutrientlist fn " +
            "WHERE f.categoryId.id = :categoryId AND " +
            "fn.nutrient.name IN :names AND " +
            "fn.amount BETWEEN :min AND :max")
    Page<Food> findAllByNutrientsInRange(@Param("categoryId")Long category, @Param("names") List<String> names,
                                         @Param("min") Integer min, @Param("max") Integer max, Pageable pageable);

    //대분류 카테고리로 했을때
    @Query("SELECT f FROM Food f " +
            "JOIN f.nutrientlist fn " +
            "WHERE f.categoryId.parentCategory.id = :categoryId AND " +
            "fn.nutrient.name IN :names AND " +
            "fn.amount BETWEEN :min AND :max")
    Page<Food> findAllByNutrientsParentCategoryInRange(@Param("categoryId")Long category, @Param("names") List<String> names,
                                         @Param("min") Integer min, @Param("max") Integer max, Pageable pageable);

    Food findByFoodCode(String foodcode);

    List<Food> findByNameStartingWith(String prefix, Pageable pageable);
}

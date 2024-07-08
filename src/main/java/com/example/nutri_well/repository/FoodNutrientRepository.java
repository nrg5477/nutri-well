package com.example.nutri_well.repository;

import com.example.nutri_well.entity.FoodNutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodNutrientRepository extends JpaRepository<FoodNutrient,Long> {
}
package com.example.nutri_well.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 식품에 함유된 영양소의 양과 식품&영양소 사이의 관계를 정의한 엔티티.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foodNutrient")
public class FoodNutrient {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "foodId")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "nutrientId")
    private Nutrient nutrient;

    private double amount;

    public FoodNutrient(Food food, Nutrient nutrient, double amount) {
        this.food = food;
        this.nutrient = nutrient;
        this.amount = amount;
    }
}

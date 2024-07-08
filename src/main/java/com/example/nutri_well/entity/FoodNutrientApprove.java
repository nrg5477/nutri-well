package com.example.nutri_well.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 승인요청된 식품에 대한 영양소의 승인을 나타내는 엔티티.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foodNutrientApprove")
public class FoodNutrientApprove {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "foodApproveId")
    private FoodApprove foodApprove;

    @ManyToOne
    @JoinColumn(name = "nutrientId")
    private Nutrient nutrient;

    private double amount;

    public FoodNutrientApprove(FoodApprove foodApprove, Nutrient nutrient, double amount) {
        this.foodApprove = foodApprove;
        this.nutrient = nutrient;
        this.amount = amount;
    }
}

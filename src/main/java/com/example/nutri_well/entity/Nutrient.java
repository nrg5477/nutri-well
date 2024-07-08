package com.example.nutri_well.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 영양소를 나타내는 엔티티.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nutrient")
public class Nutrient {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String servingUnit;

    @OneToMany(mappedBy = "nutrient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodNutrient> foodlist = new ArrayList<>();

    public Nutrient(String name, String servingUnit) {
        this.name = name;
        this.servingUnit = servingUnit;
    }
}

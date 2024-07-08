package com.example.nutri_well.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 식품의 정보 및 attributes를 정의한 식품 엔티티.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food")
@Builder
public class Food {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category categoryId;

    private String foodCode;
    private String product;

    private String manufacturer;
    private String servingSize;
    private int weight;

    private Date creationDate;

    //하나의 식품은 여러개의 영양소를 가질수 있다.
    @ToString.Exclude
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodNutrient> nutrientlist = new ArrayList<>();

    @ToString.Exclude
    @OneToMany
    private List<BookMark> userlist = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarFood> calendarFoods = new ArrayList<>();

    @Builder
    public Food(String name, Category categoryId, String foodCode, String product, String manufacturer, String servingSize, int weight, Date creationDate) {
        this.name = name;
        this.categoryId = categoryId;
        this.foodCode = foodCode;
        this.product = product;
        this.manufacturer = manufacturer;
        this.servingSize = servingSize;
        this.creationDate = creationDate;
        this.weight = weight;
    }
}
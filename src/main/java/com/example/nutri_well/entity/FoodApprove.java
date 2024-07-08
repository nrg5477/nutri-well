package com.example.nutri_well.entity;

import com.example.nutri_well.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 식품승인받을 식품에 대해 정의한 엔티티.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foodApprove")
public class FoodApprove {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category categoryId;

    private String product; // 식품 기원명
    private Date requestDate; // 신청일
    private Date approvalDate; // 승인일
    private String manufacturer;
    private String servingSize; // 1회 제공량
    private int foodWeight;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private boolean approved; // 승인여부

    @ToString.Exclude
    @OneToMany(mappedBy = "foodApprove", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodNutrientApprove> nutrientlist = new ArrayList<>();
}
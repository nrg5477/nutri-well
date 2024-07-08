package com.example.nutri_well.entity;

import com.example.nutri_well.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사용자가 담기한 식품 항목에 대한 정보가 포함된 영양차트를 나타냄.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;

    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "foodId")
    private Food foodId;

    private double percent;//기ㅏ초대사량 퍼센티지

    public Basket(User userId, LocalDate startDate, Food foodId) {
        this.userId = userId;
        this.startDate = startDate;
        this.foodId = foodId;
    }
}

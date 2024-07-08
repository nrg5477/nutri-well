package com.example.nutri_well.entity;

import com.example.nutri_well.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

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

    private double percent; //기초대사량 퍼센티지

    public Basket(User userId, LocalDate startDate, Food foodId) {
        this.userId = userId;
        this.startDate = startDate;
        this.foodId = foodId;
    }
}
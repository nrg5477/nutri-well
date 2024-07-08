package com.example.nutri_well.dto;

import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponseDTO {
    private Food food;
    private User user;
    private LocalDate date;
    private double persent;

    public static BasketResponseDTO of(Basket basket) {
        return new BasketResponseDTO(basket.getFoodId(), basket.getUserId(), basket.getStartDate(), basket.getPercent());
    }
}

package com.example.nutri_well.dto;

import com.example.nutri_well.entity.Chart;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartResponseDTO {
    private Food food;
    private User user;
    private LocalDate date;
    private double persent;

    public static ChartResponseDTO of(Chart chart) {
        return new ChartResponseDTO(chart.getFoodId(), chart.getUserId(), chart.getStartDate(), chart.getPercent());
    }
}

package com.example.nutri_well.service;

import com.example.nutri_well.dto.ChartResponseDTO;
import com.example.nutri_well.entity.Chart;
import com.example.nutri_well.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ChartService {
    ChartResponseDTO insert(Chart dto);

    void saveToCalendar(User user, List<Long> foodIds, LocalDate date, double kcalPercentage);

    void deleteUser(Long userId);
}
package com.example.nutri_well.dto;

import lombok.Data;

import java.util.List;

@Data
public class CalendarSaveRequest {
    private Long userId;
    private List<Long> foodIds;
    private double kcalPercentage;
}
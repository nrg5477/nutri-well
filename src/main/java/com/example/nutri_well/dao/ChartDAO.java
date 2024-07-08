package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Chart;

import java.time.LocalDate;

public interface ChartDAO {
    Chart insert(Chart dto); // 캘린더 등록
    void delete(Long userId, LocalDate startDate); // 담은 식품 삭제
}
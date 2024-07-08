package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Basket;

import java.time.LocalDate;

public interface BasketDAO {
    Basket insert(Basket dto); // 캘린더 등록

    void delete(Long userId, LocalDate startDate); // 담은 식품 삭제
}
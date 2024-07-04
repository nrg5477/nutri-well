package com.example.nutri_well.service;

import com.example.nutri_well.dto.BasketResponseDTO;
import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.model.User;

import java.time.LocalDate;
import java.util.List;

public interface BasketService {
	BasketResponseDTO insert(Basket dto) ;


	void saveToCalendar(User user, List<Long> foodIds, LocalDate date, double kcalPercentage);
	void deleteUser(Long userId);
}

package com.example.nutri_well.service;

import com.example.nutri_well.dto.BasketResponseDTO;
import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.entity.Food;

import java.time.LocalDate;

public interface BasketService {
	BasketResponseDTO insert(Basket dto) ;
	void delete(Long userId);
}

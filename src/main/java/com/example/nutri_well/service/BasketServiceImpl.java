package com.example.nutri_well.service;

import com.example.nutri_well.dao.BasketDAO;
import com.example.nutri_well.dto.BasketResponseDTO;
import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.entity.FoodNutrient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {
    private  final BasketDAO dao;

    @Override
    public BasketResponseDTO insert(Basket dto) {
        double energyAmount = dto.getFoodId().getNutrientlist().stream()
                .filter(nutrient -> "에너지".equals(nutrient.getNutrient().getName()))
                .mapToDouble(FoodNutrient::getAmount)
                .findFirst()
                .orElse(0);
        int BaselMetabolism = dto.getUserId().getBaselMetabolism();
        if(BaselMetabolism == 0){
            BaselMetabolism = 2000;
        };
        double weight = (double) dto.getFoodId().getWeight() /100;
        double totalKcal = (int) (energyAmount * weight);
        double percent = totalKcal/BaselMetabolism * 100;
        dto.setPercent(percent);
        System.out.println("========================================="+dto.getPercent());
        Basket basket = dao.insert(dto);
        BasketResponseDTO basketResponseDTO = BasketResponseDTO.of(basket);
        return basketResponseDTO;
    }

    @Override
    public void delete(Long userId) {
        dao.delete(userId,LocalDate.now());
    }
}

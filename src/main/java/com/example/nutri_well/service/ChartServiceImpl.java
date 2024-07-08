package com.example.nutri_well.service;

import com.example.nutri_well.dao.ChartDAO;
import com.example.nutri_well.dto.ChartResponseDTO;
import com.example.nutri_well.entity.Chart;
import com.example.nutri_well.entity.CalendarFood;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.entity.FoodNutrient;
import com.example.nutri_well.model.User;
import com.example.nutri_well.model.myCalendar;
import com.example.nutri_well.repository.CalendarFoodRepository;
import com.example.nutri_well.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChartServiceImpl implements ChartService {
    private final ChartDAO dao;
    private final CalendarRepository calendarRepository;
    private final CalendarFoodRepository calendarFoodRepository;
    private final FoodService foodService;

    @Override
    public ChartResponseDTO insert(Chart dto) {
        //개인 기초대사량에 맞춰 음식별 기초대사량 퍼센트 계산
        double energyAmount = dto.getFoodId().getNutrientlist().stream()
                .filter(nutrient -> "에너지".equals(nutrient.getNutrient().getName()))
                .mapToDouble(FoodNutrient::getAmount)
                .findFirst()
                .orElse(0);
        int BaselMetabolism = dto.getUserId().getBaselMetabolism();
        if (BaselMetabolism == 0) {
            BaselMetabolism = 2000;
        };

        double weight = (double) dto.getFoodId().getWeight() / 100;
        double totalKcal = (int) (energyAmount * weight);
        double percent = totalKcal / BaselMetabolism * 100;
        dto.setPercent(percent);

        Chart chart = dao.insert(dto);
        ChartResponseDTO chartResponseDTO = ChartResponseDTO.of(chart);
        return chartResponseDTO;
    }

    @Override
    public void deleteUser(Long userId) {
        dao.delete(userId, LocalDate.now());
    }

    @Override
    public void saveToCalendar(User user, List<Long> foodIds, LocalDate date, double kcalPercentage) {
        myCalendar calendar = myCalendar.builder()
                .user(user)
                .calDate(date)
                .percentage((int) kcalPercentage)
                .build();
        calendarRepository.save(calendar);

        for (Long foodId : foodIds) {
            Food food = foodService.findEntityById(foodId);
            CalendarFood calendarFood = CalendarFood.builder()
                    .calendar(calendar)
                    .food(food)
                    .build();
            calendarFoodRepository.save(calendarFood);
        }
    }

}

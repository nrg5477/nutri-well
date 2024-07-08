package com.example.nutri_well.service;

import com.example.nutri_well.dto.DailyNutritionResponse;
import com.example.nutri_well.entity.CalendarFood;
import com.example.nutri_well.entity.FoodNutrient;
import com.example.nutri_well.model.User;
import com.example.nutri_well.model.myCalendar;
import com.example.nutri_well.repository.CalendarFoodRepository;
import com.example.nutri_well.repository.CalendarRepository;
import com.example.nutri_well.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final CalendarFoodRepository calendarFoodRepository;

    public myCalendar addCalendarEntry(Long userId, Date date, int percentage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        myCalendar calendar = myCalendar.builder()
                .user(user)
                .calDate(localDate)
                .percentage(percentage)
                .build();

        return calendarRepository.save(calendar);
    }

    public List<myCalendar> getCalendarEntries(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return calendarRepository.findByUser(user);
    }


    public DailyNutritionResponse getDailyNutrition(Long calendarId) {
        List<CalendarFood> calendarFoods = calendarFoodRepository.findByCalendar_CalendarId(calendarId);
        DailyNutritionResponse response = new DailyNutritionResponse();

        for (CalendarFood calendarFood : calendarFoods) {
            double weight = calendarFood.getFood().getWeight();
            double servingSize = Double.parseDouble(calendarFood.getFood().getServingSize());
            for (FoodNutrient foodNutrient : calendarFood.getFood().getNutrientlist()) {
                response.addNutrient(foodNutrient.getNutrient().getName(), foodNutrient.getAmount() * weight / servingSize);
            }
        }

        return response;
    }

    public List<myCalendar> getAllCalendars() {
        return calendarRepository.findAll();
    }
}

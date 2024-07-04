package com.example.nutri_well.repository;

import com.example.nutri_well.entity.CalendarFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarFoodRepository extends JpaRepository<CalendarFood, Long> {
    List<CalendarFood> findByCalendar_CalendarId(Long calendarId);
}
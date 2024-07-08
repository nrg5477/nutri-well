package com.example.nutri_well.repository;

import com.example.nutri_well.model.User;
import com.example.nutri_well.model.myCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<myCalendar, Long> {
    List<myCalendar> findByUser(User user);
}
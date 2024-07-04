package com.example.nutri_well.entity;

import com.example.nutri_well.model.myCalendar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CALENDAR_FOOD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarFood {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendarId", nullable = false)
    private myCalendar calendar;

    @ManyToOne
    @JoinColumn(name = "foodId", nullable = false)
    private Food food;
}

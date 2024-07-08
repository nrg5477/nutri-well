package com.example.nutri_well.model;

import com.example.nutri_well.entity.CalendarFood;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 사용자별 캘린더를 나타내는 엔티티.
 */
@Entity
@Table(name = "CALENDAR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class myCalendar {
    @Id
    @GeneratedValue
    private Long calendarId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate calDate;

    @Column(nullable = false)
    private int percentage;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarFood> foods;
}
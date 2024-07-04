package com.example.nutri_well.model;

import com.example.nutri_well.entity.CalendarFood;
import com.example.nutri_well.entity.Food;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @Builder
    public myCalendar(User user, LocalDate calDate, int percentage) {
        this.user = user;
        this.calDate = calDate;
        this.percentage = percentage;
    }
}

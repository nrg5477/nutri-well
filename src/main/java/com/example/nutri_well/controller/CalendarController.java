package com.example.nutri_well.controller;

import com.example.nutri_well.config.auth.dto.SessionUser;
import com.example.nutri_well.dto.DailyNutritionRequest;
import com.example.nutri_well.dto.DailyNutritionResponse;
import com.example.nutri_well.model.User;
import com.example.nutri_well.model.myCalendar;
import com.example.nutri_well.service.CalendarService;
import com.example.nutri_well.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final HttpSession httpSession;
    private final UserService userService;

    @PostMapping("/add")
    public myCalendar addCalendarEntry(@RequestParam Long userId, @RequestParam Date date, @RequestParam int percentage) {
        return calendarService.addCalendarEntry(userId, date, percentage);
    }

    @GetMapping("/user/{userId}")
    public List<myCalendar> getCalendarEntries(@PathVariable Long userId) {
        return calendarService.getCalendarEntries(userId);
    }

    @GetMapping("/api/calendars")
    public List<Map<String, Object>> getCalendars() {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser != null) {
            Optional<User> userOptional = userService.findByUserEmail(sessionUser.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return user.getCalendars().stream()
                        .map(calendar -> {
                            Map<String, Object> map = Map.of(
                                    "id", calendar.getCalendarId(),
                                    "title", calendar.getPercentage() + "%",
                                    "start", calendar.getCalDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toString(),
                                    "color", getColorByPercentage(calendar.getPercentage())
                            );
                            return map;
                        }).collect(Collectors.toList());
            }
        }
        return List.of();
    }

    private String getColorByPercentage(int percentage) {
        if (percentage > 90) {
            return "#006400"; // DarkGreen
        } else if (percentage > 75) {
            return "#228B22"; // ForestGreen
        } else if (percentage > 60) {
            return "#32CD32"; // LimeGreen
        } else if (percentage > 45) {
            return "#3CB371"; // MediumSeaGreen
        } else if (percentage > 30) {
            return "#66CDAA"; // MediumAquamarine
        } else if (percentage > 15) {
            return "#98FB98"; // PaleGreen
        } else {
            return "#00FF00"; // Lime
        }
    }

    @PostMapping("/api/dailyNutrition")
    public DailyNutritionResponse getDailyNutrition(@RequestBody DailyNutritionRequest request) {
        return calendarService.getDailyNutrition(request.getCalendarId());//영양소 별 이름과 양을 반환
    }
}

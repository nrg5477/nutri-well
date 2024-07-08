package com.example.nutri_well.controller;

import com.example.nutri_well.dao.FoodDAO;
import com.example.nutri_well.dto.CalendarSaveRequest;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.entity.Chart;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.model.User;
import com.example.nutri_well.service.ChartService;
import com.example.nutri_well.service.BookMarkService;
import com.example.nutri_well.service.FoodService;
import com.example.nutri_well.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 영양차트와 관련된 기능 또는 관련된 요청을 처리하는 컨트롤러 클래스.
 */
@RestController
@RequestMapping("/chart")
@RequiredArgsConstructor
public class ChartController {
    private final BookMarkService bookMarkService;
    private final FoodService foodService;
    private final FoodDAO foodDAO;
    private final UserService userService;
    private final ChartService chartService;

    @PostMapping("/insert")
    public FoodResponseDTO nutriInsert(@RequestParam Long foodId, @RequestParam(required = false) Long userId) {
        FoodResponseDTO fooddto;
        if (userId != null) { //로그인시 DB저장
            Food food = foodDAO.findById(foodId);
            User user = userService.findById(userId).get();
            chartService.insert(new Chart(user, LocalDate.now(), food));
        }
        fooddto = foodService.findById(foodId);
        return fooddto;
    }

    @PostMapping("/getBookMark")
    public List<FoodResponseDTO> getBookMark(@RequestParam Long userId) {
        return bookMarkService.findFoodNamesByUserId(userId);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("userId") Long userId) {
        chartService.deleteUser(userId);
    }

    @PostMapping("/saveCalendar")
    @ResponseBody
    public String saveCalendar(@RequestBody CalendarSaveRequest request) {
        //chart.js에서 chart/saveCalendar 호출 -> saveToCalendar 함수로
        //calendar 테이블 에는 user, date, kcalPercentage 저장
        //calendarFood 테이블 에는 calendarId, foodId 저장
        User user = userService.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        chartService.saveToCalendar(user, request.getFoodIds(), LocalDate.now(), request.getKcalPercentage());

        return "캘린더 저장 완료";
    }
}
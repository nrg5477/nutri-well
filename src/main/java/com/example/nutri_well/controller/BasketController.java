package com.example.nutri_well.controller;

import com.example.nutri_well.dao.FoodDAO;
import com.example.nutri_well.dto.CalendarSaveRequest;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.model.User;
import com.example.nutri_well.service.BasketService;
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
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BookMarkService bookMarkService;
    private final FoodService foodService;
    private final FoodDAO foodDAO;
    private final UserService userService;
    private final BasketService basketService;

    @PostMapping("/insert")
    public FoodResponseDTO nutriInsert(@RequestParam Long foodId, @RequestParam(required = false) Long userId) {//유저아이디바당야댐
        FoodResponseDTO fooddto = null;
        if (userId != null) {//로그인시 DB저장
            fooddto = foodService.findById(foodId);
            Food food = foodDAO.findById(foodId);
            User user = userService.findById(userId).get();
            basketService.insert(new Basket(user, LocalDate.now(), food));
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
        basketService.deleteUser(userId);
    }

    @PostMapping("/saveCalendar")
    @ResponseBody
    public String saveCalendar(@RequestBody CalendarSaveRequest request) {
        //basket.js에서 basket/saveCalendar 호출 -> saveToCalendar 함수로
        //calendar 테이블 에는 user, date, kcalPercentage 저장
        //calendarFood 테이블 에는 calendarId, foodId 저장
        User user = userService.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        System.out.println(user.getUserId()+"&&&&&&&&&&&&&"+request.getFoodIds()); //여기서 null로 들어오는게 문제
        basketService.saveToCalendar(user, request.getFoodIds(), LocalDate.now(), request.getKcalPercentage());

        return "캘린더 저장 완료";
    }
}

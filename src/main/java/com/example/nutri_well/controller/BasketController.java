package com.example.nutri_well.controller;

import com.example.nutri_well.dao.FoodDAO;
import com.example.nutri_well.dto.BookMarkResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.model.User;
import com.example.nutri_well.service.*;
import com.example.nutri_well.entity.Food;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService service;
    private final BookMarkService bookMarkService;
    private final FoodService foodService;
    private final FoodDAO foodDAO;
    private final UserService userService;
    private final BasketService basketService;

    @PostMapping("/insert")
    public FoodResponseDTO nutriInsert(@RequestParam("foodId") Long foodId, @RequestParam(value = "userId",required = false) Long userId) {//유저아이디바당야댐
        FoodResponseDTO fooddto = null;
        if(userId != null){//로그인시 DB저장
            fooddto = foodService.findById(foodId);
            Food food = foodDAO.findById(foodId);
            User user = userService.findById(userId).get();
            basketService.insert(new Basket(user, LocalDate.now(),food));
        }
        fooddto = foodService.findById(foodId);
        return fooddto;
    }

    @PostMapping("/getBookMark")
    public List<FoodResponseDTO> getBookMark(@RequestParam("userId") Long userId) {
        return bookMarkService.findFoodNamesByUserId(userId);
    }
    @PostMapping("/delete")
    public void delete(@RequestParam("userId") Long userId) {
        basketService.delete(userId);
    }
}

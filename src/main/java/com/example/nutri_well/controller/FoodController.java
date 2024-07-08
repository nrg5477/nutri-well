package com.example.nutri_well.controller;

import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.service.CategoryService;
import com.example.nutri_well.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 식품과 관련된 기능 또는 관련된 요청을 처리하는 컨트롤러 클래스.
 * 특정 식품에 대한 상세보기를 보여주는 역할처리.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;
    private final CategoryService categoryService;

    //식품 상세 페이지 view
    @GetMapping("/detail")
    public ModelAndView showPage(@RequestParam Long foodId){
        ModelAndView mav = new ModelAndView("/food/food_detail");
        FoodResponseDTO dto = foodService.findById(foodId);
        mav.addObject("categories", categoryService.findByParentCategoryIsNull());
        mav.addObject("food", dto);
        mav.addObject("foodId", dto.getId());
        return mav;
    }
}

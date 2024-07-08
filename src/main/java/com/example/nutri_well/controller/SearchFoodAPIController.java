package com.example.nutri_well.controller;

import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.dto.FoodSuggestResponseDTO;
import com.example.nutri_well.service.CategoryService;
import com.example.nutri_well.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  음식 검색과 관련된 기능 또는 관련된  API 요청을 처리하는 컨트롤러 클래스.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public final class SearchFoodAPIController {
    private final FoodService foodService;
    private final CategoryService categoryService;

    //식품 상세 페이지 음식정보 전달
    @GetMapping("/food/detail")
    public FoodResponseDTO showPage(@RequestParam("foodId") Long foodId){
        return foodService.findById(foodId);
    }

    //검색어 자동 완성
    @GetMapping("/auto/search")
    public List<FoodSuggestResponseDTO> search(@RequestParam String query) {
        PageRequest pageRequest = PageRequest.of(0, 5);
        return foodService.findByNameStartingWith(query,pageRequest);
    }
}
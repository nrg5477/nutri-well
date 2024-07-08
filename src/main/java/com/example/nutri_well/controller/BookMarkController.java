package com.example.nutri_well.controller;

import com.example.nutri_well.dto.BookMarkRequestDTO;
import com.example.nutri_well.dto.BookMarkResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 즐겨찾기와 관련된 기능 또는 관련된 요청을 처리하는 컨트롤러 클래스.
 */
@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    //즐겨찾기 DB저장
    @PostMapping("/favorited")
    public BookMarkResponseDTO savePreferred(@RequestBody BookMarkRequestDTO bookMark){
        BookMarkResponseDTO update = bookMarkService.updateStates(bookMark,true);
        return update;
    }

    //제외식품 DB저장
    @PostMapping("/excluded")
    public BookMarkResponseDTO saveExcluded(@RequestBody BookMarkRequestDTO bookMark){
        BookMarkResponseDTO update = bookMarkService.updateStates(bookMark,false);
        return update;
    }

    //상세페이지 로드시 즐겨찾기값 받아오기
    @PostMapping("/check")
    public BookMarkResponseDTO checkFood(@RequestParam Long foodId, @RequestParam Long userId){
        BookMarkResponseDTO update = bookMarkService.findByFoodIdAndUserId(foodId, userId);
        return update;
    }

    //페이지 로드시 즐겨찾기 많은순 list
    @PostMapping("/preferredlist")
    public List<FoodResponseDTO> loadFood() {
        List<FoodResponseDTO> top5Foods = bookMarkService.findTop4Foods();
        return top5Foods;
    }
}

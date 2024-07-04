package com.example.nutri_well.controller;

import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.model.User;
import com.example.nutri_well.repository.CategoryRepository;
import com.example.nutri_well.repository.FoodRepository;
import com.example.nutri_well.service.BasketService;
import com.example.nutri_well.service.CategoryService;
import com.example.nutri_well.service.FoodService;
import com.example.nutri_well.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public final class SearchFoodController {
    private final FoodService foodService;
    private final CategoryService categoryService;
    @GetMapping("/search")
    public ModelAndView searchPage(@RequestParam("query") String query, @RequestParam("page") int page, @RequestParam("size") int size,
                                   @RequestParam(name="nutrients",required = false) List<String> nutrients,
                                   @RequestParam(name="min",required = false) Integer min,
                                   @RequestParam(name="max",required = false) Integer max){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<FoodResponseDTO> foodlist = null;

        if(nutrients != null || min != null || max != null){
            //foodlist = foodService.findAllByNutrientsNotIn(query,nutrients,pageRequest);
            foodlist = foodService.findAllByNutrientsInRange(query,nutrients,min,max,pageRequest);
        }else{
            foodlist = foodService.searchByFoodName(query, pageRequest);
        }

        int totalpage = foodService.getTotalPages();
        List<CategoryResponseDTO> categories = categoryService.findByParentCategoryIsNull();

        ModelAndView mav = new ModelAndView("/search/search");
        mav.addObject("query",query);
        mav.addObject("totalPage",totalpage);
        mav.addObject("foodlist",foodlist);
        mav.addObject("categories",categories);
        return mav;
    }
    @GetMapping("/searchCategory")
    public ModelAndView searchCategoryPage(@RequestParam(name="category",required = false) Long category,
                                           @RequestParam("page") int page, @RequestParam("size") int size,
                                           @RequestParam(name="nutrients",required = false) List<String> nutrients,
                                           @RequestParam(name="min",required = false) Integer min,
                                           @RequestParam(name="max",required = false) Integer max){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<FoodResponseDTO> foodlist = null;

        CategoryResponseDTO categoryDTO = categoryService.findbyId(category);
        if(nutrients != null || min != null || max != null){
            //foodlist = foodService.findAllByNutrientsNotIn(categoryDTO,nutrients,pageRequest);
            foodlist = foodService.findAllByNutrientsInRange(category,nutrients,min,max,pageRequest);
        }else {
            foodlist = foodService.searchByCategoryId(categoryDTO, pageRequest);
        }
        int totalpage = foodService.getTotalPages();

        List<CategoryResponseDTO> categories = categoryService.findByParentCategoryIsNull();
        ModelAndView mav = new ModelAndView("/search/search");
        mav.addObject("totalPage",totalpage);
        mav.addObject("category",category);
        mav.addObject("foodlist",foodlist);
        mav.addObject("categories",categories);
        return mav;
    }

    @GetMapping("/show")
    public ModelAndView searchPage2( ) {
        ModelAndView mav = new ModelAndView("search/search");
        return mav;
    }
}

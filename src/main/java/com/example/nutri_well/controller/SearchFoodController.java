package com.example.nutri_well.controller;

import com.example.nutri_well.dao.CategoryDAO;
import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.entity.Category;
import com.example.nutri_well.service.CategoryService;
import com.example.nutri_well.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 음식 및 카테고리에 대한 검색과 관련된 기능 또는 관련된 요청을 처리하는 컨트롤러 클래스.
 */
@RequiredArgsConstructor
@Controller
public final class SearchFoodController {
    private final FoodService foodService;
    private final CategoryService categoryService;
    private final CategoryDAO dao;

    @GetMapping("/search")
    public ModelAndView searchPage(@RequestParam("query") String query, @RequestParam("page") int page, @RequestParam("size") int size,
                                   @RequestParam(name = "nutrients", required = false) List<String> nutrients,
                                   @RequestParam(name = "min", required = false) Integer min,
                                   @RequestParam(name = "max", required = false) Integer max) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<FoodResponseDTO> foodlist = null;

        if (nutrients != null || min != null || max != null) {
            //foodlist = foodService.findAllByNutrientsNotIn(query,nutrients,pageRequest);
            foodlist = foodService.findAllByNutrientsInRange(query, nutrients, min, max, pageRequest);
        } else {
            foodlist = foodService.searchByFoodName(query, pageRequest);
        }

        int totalpage = foodService.getTotalPages();
        List<CategoryResponseDTO> categories = categoryService.findByParentCategoryIsNull();

        ModelAndView mav = new ModelAndView("/search/search");
        mav.addObject("query", query);
        mav.addObject("totalPage", totalpage);
        mav.addObject("foodlist", foodlist);
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/searchCategory")
    public ModelAndView searchCategoryPage(@RequestParam(name = "category", required = false) Long category,
                                           @RequestParam("page") int page, @RequestParam("size") int size,
                                           @RequestParam(name = "nutrients", required = false) List<String> nutrients,
                                           @RequestParam(name = "min", required = false) Integer min,
                                           @RequestParam(name = "max", required = false) Integer max) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<FoodResponseDTO> foodlist = null;

        CategoryResponseDTO categoryDTO = categoryService.findbyId(category);
        Category parentCategory = dao.findbyId(category);
        if ((nutrients != null || min != null || max != null) && (parentCategory.getId().intValue() > 0 && parentCategory.getId().intValue() < 22)) {
            //foodlist = foodService.findAllByNutrientsNotIn(categoryDTO,nutrients,pageRequest);
            foodlist = foodService.findAllByNutrientsParentCategoryInRange(category, nutrients, min, max, pageRequest);
        } else if (nutrients != null || min != null || max != null) {
            foodlist = foodService.findAllByNutrientsInRange(category, nutrients, min, max, pageRequest);
        } else if (parentCategory.getId().intValue() > 0 && parentCategory.getId().intValue() < 22) {
            foodlist = foodService.findByparentCategoryFood(parentCategory, pageRequest);
        } else {
            foodlist = foodService.searchByCategoryId(categoryDTO, pageRequest);
        }
        int totalpage = foodService.getTotalPages();

        List<CategoryResponseDTO> categories = categoryService.findByParentCategoryIsNull();
        ModelAndView mav = new ModelAndView("/search/search");
        mav.addObject("totalPage", totalpage);
        mav.addObject("category", category);
        mav.addObject("foodlist", foodlist);
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/show")
    public ModelAndView searchPage2() {
        ModelAndView mav = new ModelAndView("search/search");
        return mav;
    }
}

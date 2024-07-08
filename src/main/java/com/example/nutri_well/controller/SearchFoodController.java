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
    public ModelAndView searchPage(@RequestParam String query, @RequestParam int page, @RequestParam int size,
                                   @RequestParam(required = false) List<String> nutrients,
                                   @RequestParam(required = false) Integer min,
                                   @RequestParam(required = false) Integer max){

        List<FoodResponseDTO> foodlist = getFoodList(query, null, page, size, nutrients, min, max);
        List<CategoryResponseDTO> categories = categoryService.findByParentCategoryIsNull();
        int totalpage = foodService.getTotalPages();

        ModelAndView mav = new ModelAndView("/search/search");
        mav.addObject("query", query);
        mav.addObject("totalPage", totalpage);
        mav.addObject("foodlist", foodlist);
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/searchCategory")
    public ModelAndView searchCategoryPage(@RequestParam(required = false) Long category,
                                           @RequestParam int page, @RequestParam int size,
                                           @RequestParam(required = false) List<String> nutrients,
                                           @RequestParam(required = false) Integer min,
                                           @RequestParam(required = false) Integer max) {

        CategoryResponseDTO categoryDTO = categoryService.findbyId(category);
        Category parentCategory = dao.findbyId(category);
        List<FoodResponseDTO> foodlist = getFoodList(categoryDTO, parentCategory, page, size, nutrients, min, max);
        List<CategoryResponseDTO> categories = categoryService.findByParentCategoryIsNull();
        int totalpage = foodService.getTotalPages();

        ModelAndView mav = new ModelAndView("/search/search");
        mav.addObject("category", category);
        mav.addObject("totalPage", totalpage);
        mav.addObject("foodlist", foodlist);
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/show")
    public ModelAndView searchPage2() {
        return new ModelAndView("search/search");
    }

    private List<FoodResponseDTO> getFoodList(Object queryOrCategory, Category parentCategory,
                                              int page, int size, List<String> nutrients, Integer min, Integer max) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        List<FoodResponseDTO> foodlist = null;

        boolean isScopeSearch = nutrients != null || min != null || max != null;

        if (queryOrCategory instanceof String) {//검색어 입력시
            String query = (String) queryOrCategory;
            if (isScopeSearch) { //범위검색 조회
                foodlist = foodService.findAllByNutrientsInRange(query, nutrients, min, max, pageRequest);
            } else { //검색어 입력시 조회
                foodlist = foodService.searchByFoodName(query, pageRequest);
            }
        } else if (queryOrCategory instanceof CategoryResponseDTO) {//카테고리별 검색시
            Long categoryId = ((CategoryResponseDTO) queryOrCategory).getCategoryId();
            if (isScopeSearch && parentCategory != null && parentCategory.getId().intValue() > 0 && parentCategory.getId().intValue() < 22) {
                //메인 페이지 대분류 검색
                foodlist = foodService.findAllByNutrientsParentCategoryInRange(categoryId, nutrients, min, max, pageRequest);
            } else if (isScopeSearch) {
                //카테고리로 범위검색시 조회
                foodlist = foodService.findAllByNutrientsInRange(categoryId, nutrients, min, max, pageRequest);
            } else if (parentCategory != null && parentCategory.getId().intValue() > 0 && parentCategory.getId().intValue() < 22) {
                //대분류 카테고리 검색
                foodlist = foodService.findByparentCategoryFood(parentCategory, pageRequest);
            } else {
                //카테고리 ID로 검색
                foodlist = foodService.searchByCategoryId((CategoryResponseDTO) queryOrCategory, pageRequest);
            }
        }
        return foodlist;
    }
}

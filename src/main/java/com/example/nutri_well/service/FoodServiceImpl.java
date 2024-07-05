package com.example.nutri_well.service;

import com.example.nutri_well.dao.FoodDAO;
import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.dto.FoodNutrientResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.dto.FoodSuggestResponseDTO;
import com.example.nutri_well.entity.Category;
import com.example.nutri_well.entity.Food;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService{
    private final FoodDAO dao;
    private int totalPage;

    @Override
    public List<FoodResponseDTO> searchByFoodName(String name, Pageable pageable) {
        Page<Food> foods = dao.searchByFoodName(name, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();

        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }
        this.totalPage = foods.getTotalPages();

        return list;
    }

    @Override
    public List<FoodResponseDTO> searchByCategoryId(CategoryResponseDTO category, Pageable pageable) {
        ModelMapper mapper = new ModelMapper();
        Category entity = mapper.map(category, Category.class);

        Page<Food> foods = dao.searchByCategoryId(entity.getId(), pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();

        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }

        this.totalPage = foods.getTotalPages();
        return list;
    }

    @Override
    public List<FoodResponseDTO> findByparentCategoryFood(Category category, Pageable pageable) {
        Page<Food> foods = dao.findByparentCategoryFood(category, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();

        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }

        this.totalPage = foods.getTotalPages();
        return list;
    }

    @Override
    public List<FoodResponseDTO> findAllByNutrientsNotIn(String foodname, List<String> names ,Pageable pageable) {
        String namepattern = "%"+foodname+"%";

        Page<Food> foods = dao.findAllByNutrientsNotIn(namepattern, names, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();

        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }

        this.totalPage = foods.getTotalPages();
        return list;
    }

    @Override
    public List<FoodResponseDTO> findAllByNutrientsNotIn(CategoryResponseDTO category, List<String> names, Pageable pageable) {
        ModelMapper mapper = new ModelMapper();
        Category entity = mapper.map(category, Category.class);

        Page<Food> foods = dao.findAllByNutrientsNotIn(entity.getId(),names, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();

        this.totalPage = foods.getTotalPages();
        return list;
    }

    @Override
    public int getTotalPages() {
        return totalPage;
    }

    @Override
    public FoodResponseDTO findByName(String name) {
        return FoodResponseDTO.of(dao.findByName(name));
    }

    @Override
    public FoodResponseDTO findByFoodCode(String foodcode) {
        return FoodResponseDTO.of(dao.findByFoodCode(foodcode));
    }

    @Override
    public List<FoodSuggestResponseDTO> findByNameStartingWith(String prefix, Pageable pageable) {
        List<Food> foods = dao.findByNameStartingWith(prefix,pageable);
        List<FoodSuggestResponseDTO> list = new ArrayList<>();
        for (Food food : foods) {
            list.add(FoodSuggestResponseDTO.of(food));
        }
        return list;
    }

    @Override
    public FoodResponseDTO findById(Long foodId) {
        Food food = dao.findById(foodId);
        return FoodResponseDTO.of(food);
    }

    @Override
    public List<FoodResponseDTO> findAllByNutrientsInRange(String foodname, List<String> names, Integer min, Integer max, Pageable pageable) {
        String namepattern = "%"+foodname+"%";//LIKE절 처리

        Page<Food> foods = dao.findAllByNutrientsInRange(namepattern, names, min, max, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();
        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }
        this.totalPage = foods.getTotalPages();
        return list;
    }
    @Override
    public List<FoodResponseDTO> findAllByNutrientsInRange(Long categoryId, List<String> names, Integer min, Integer max, Pageable pageable) {

        Page<Food> foods = dao.findAllByNutrientsInRange(categoryId, names, min, max, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();
        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }
        this.totalPage = foods.getTotalPages();
        return list;
    }

    @Override
    public List<FoodResponseDTO> findAllByNutrientsParentCategoryInRange(Long categoryId, List<String> names, Integer min, Integer max, Pageable pageable) {

        Page<Food> foods = dao.findAllByNutrientsParentCategoryInRange(categoryId, names, min, max, pageable);
        List<FoodResponseDTO> list = foods.map(FoodResponseDTO::of).getContent();
        for (FoodResponseDTO dto : list) {
            dto.setNutrientlist(findMainNutrients(dto));//mainNutrients에 포함된 영양소만 dto에 추가
        }
        this.totalPage = foods.getTotalPages();
        return list;
    }

    @Override
    public Food findEntityById(Long foodId) {
        return dao.findById(foodId);
    }

    public List<FoodNutrientResponseDTO> findMainNutrients(FoodResponseDTO dto){
        String[] mainNutrients = {"에너지","탄수화물","단백질","지방","당류"};
        List<FoodNutrientResponseDTO> filteredNutrients = new ArrayList<>();

        for (FoodNutrientResponseDTO nutrient : dto.getNutrientlist()) {
            if (Arrays.asList(mainNutrients).contains(nutrient.getName())) {
                filteredNutrients.add(nutrient);
            }
        }
        return filteredNutrients;
    }
}

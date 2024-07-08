package com.example.nutri_well.service;

import com.example.nutri_well.dao.FoodDAO;
import com.example.nutri_well.dto.FoodApproveRequestDTO;
import com.example.nutri_well.dto.FoodApproveResponseDTO;
import com.example.nutri_well.entity.*;
import com.example.nutri_well.model.User;
import com.example.nutri_well.repository.FoodApproveRepository;
import com.example.nutri_well.repository.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodApproveServiceImpl implements FoodApproveService {
    private final FoodApproveRepository foodApproveRepository;
    private final FoodDAO foodDAO;
    private final UserService userService;
    private final NutrientRepository nutrientRepository;

    @Override
    public FoodApproveResponseDTO requestFoodApproval(FoodApproveRequestDTO requestDTO) { //추가 요청 처리
        Optional<User> user = userService.findByUserEmail(requestDTO.getUserEmail());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid user email");
        }

        FoodApprove foodApprove = new FoodApprove();
        foodApprove.setName(requestDTO.getName());
        foodApprove.setCategoryId(requestDTO.getCategoryId());
        foodApprove.setProduct(requestDTO.getProduct());
        foodApprove.setManufacturer(requestDTO.getManufacturer());
        foodApprove.setServingSize(requestDTO.getServingSize());
        foodApprove.setRequestDate(new Date());
        foodApprove.setUser(user.get());

        List<FoodNutrientApprove> nutrients = requestDTO.getNutrients().stream()
                .map(n -> {
                    Nutrient nutrient = nutrientRepository.findById(n.getNutrientId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid nutrient ID"));
                    return new FoodNutrientApprove(foodApprove, nutrient, n.getAmount());
                })
                .collect(Collectors.toList());
        foodApprove.setNutrientlist(nutrients);

        foodApproveRepository.save(foodApprove); // 해당 엔티티 db에 저장

        return FoodApproveResponseDTO.of(foodApprove);
    }

    @Override
    public FoodApproveResponseDTO approveFood(Long requestId) { //추가 요청 승인
        FoodApprove foodApprove = foodApproveRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));

        foodApprove.setApproved(true); // approved 1로 변경
        foodApprove.setApprovalDate(new Date()); // 날짜변경
        foodApproveRepository.save(foodApprove); // foodApprove 테이블 업데이트

        // 이미 존재하는 Food 엔티티가 있는지 확인
        Optional<Food> existingFood = Optional.ofNullable(foodDAO.findByName(foodApprove.getName()));

        Food food;
        if (existingFood.isPresent()) {
            // 이미 존재하는 엔티티 사용
            food = existingFood.get();
        } else {
            //해당 엔티티가 없으면 저장하는 작업.
            food = new Food(
                    foodApprove.getName(),
                    foodApprove.getCategoryId(),
                    null, // 식품코드는 null
                    foodApprove.getProduct(),
                    foodApprove.getManufacturer(),
                    foodApprove.getServingSize(),
                    100,
                    foodApprove.getApprovalDate()
            );

            for (FoodNutrientApprove nutrientApprove : foodApprove.getNutrientlist()) {
                food.getNutrientlist().add(new FoodNutrient(food, nutrientApprove.getNutrient(), nutrientApprove.getAmount()));
            }

            food = foodDAO.save(food);
        }
        return FoodApproveResponseDTO.of(foodApprove);
    }

    @Override
    public List<FoodApproveResponseDTO> getAllFoodApprovals() { // 모든 추가 요청 조회
        List<FoodApprove> foodApprovals = foodApproveRepository.findAll();
        return foodApprovals.stream()
                .map(FoodApproveResponseDTO::of)
                .collect(Collectors.toList());
    }
}
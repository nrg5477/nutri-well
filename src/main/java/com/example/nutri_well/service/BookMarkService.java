package com.example.nutri_well.service;

import com.example.nutri_well.dto.BookMarkRequestDTO;
import com.example.nutri_well.dto.BookMarkResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookMarkService {
    BookMarkResponseDTO updateStates(BookMarkRequestDTO bookMark, boolean isPreferred);

    BookMarkResponseDTO findByFoodIdAndUserId(Long foodId, Long userId);

    List<FoodResponseDTO> findTop4Foods();

    List<BookMarkResponseDTO> findByUserId(Long userId);

    List<FoodResponseDTO> findFoodNamesByUserId(Long userId);
}

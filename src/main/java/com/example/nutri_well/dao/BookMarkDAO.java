package com.example.nutri_well.dao;

import com.example.nutri_well.entity.BookMark;
import com.example.nutri_well.entity.Food;

import java.util.List;

public interface BookMarkDAO {
    BookMark update(BookMark bookMark);
    BookMark findByFoodIdAndUserId(Long foodId, Long userId);

    int updatePreferredState(Long id, boolean preferredState);
    int updateExcludedState(Long id, boolean excludedState);
    List<Food> findTop4Foods();
    List<BookMark> findByUserId(Long userId);
    List<Food> findFoodNamesByUserId(Long userId);
}

package com.example.nutri_well.dao;

import com.example.nutri_well.entity.BookMark;
import com.example.nutri_well.entity.Food;

import java.util.List;

public interface BookMarkDAO {
    BookMark update(BookMark bookMark); //즐겨찾기에 저장

    BookMark findByFoodIdAndUserId(Long foodId, Long userId); //즐겨찾기 찾기

    int updatePreferredState(Long id, boolean preferredState); //지정된 아이디로 북마크의 상태 업데이트

    int updateExcludedState(Long id, boolean excludedState); //지정된 아이디로 북마크의 상태 업데이트

    List<Food> findTop4Foods(); //상위4개 항목

    List<BookMark> findByUserId(Long userId);

    List<Food> findFoodNamesByUserId(Long userId);
}
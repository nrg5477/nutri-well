package com.example.nutri_well.service;

import com.example.nutri_well.dao.BookMarkDAO;
import com.example.nutri_well.dao.FoodDAO;
import com.example.nutri_well.dto.BookMarkRequestDTO;
import com.example.nutri_well.dto.BookMarkResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.entity.BookMark;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService{
    private final BookMarkDAO dao;
    private final UserService userService;
    private final FoodDAO foodDAO;

    @Override
    public BookMarkResponseDTO updateStates(BookMarkRequestDTO bookMark, boolean isPreferred) {
        BookMark existBookmark = dao.findByFoodIdAndUserId(bookMark.getFoodId(), bookMark.getUserId());
        boolean newState = isPreferred ? !bookMark.isPreferredState() : !bookMark.isExcludedState();

        if (existBookmark != null) {
            int result = isPreferred ?
                    dao.updatePreferredState(existBookmark.getId(), newState) :
                    dao.updateExcludedState(existBookmark.getId(), newState);

            if (result > 0) {
                if (isPreferred) {
                    existBookmark.setPreferredState(newState);
                } else {
                    existBookmark.setExcludedState(newState);
                }
            } else {
                System.out.println("업데이트안됨");
            }
        } else {
            User user = userService.findById(bookMark.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            Food food = foodDAO.findById(bookMark.getFoodId());
            existBookmark = new BookMark(food, user, isPreferred ? newState : false, isPreferred ? false : newState);
            dao.update(existBookmark);
        }
        return BookMarkResponseDTO.of(existBookmark);
    }

    @Override
    public BookMarkResponseDTO findByFoodIdAndUserId(Long foodId, Long userId) {
        BookMark bookmark = dao.findByFoodIdAndUserId(foodId, userId);
        BookMarkResponseDTO dto = new BookMarkResponseDTO();
        if(bookmark == null){
            dto.setExcludedState(false);
            dto.setPreferredState(false);
        }else{
            dto = BookMarkResponseDTO.of(dao.findByFoodIdAndUserId(foodId, userId));
        }
        return dto;
    }

    @Override
    public List<FoodResponseDTO> findTop5Foods() {
        List<Food> top5Foods = dao.findTop5Foods();
        List<FoodResponseDTO> top5FoodsList = new ArrayList<>();
        for (Food top5Food : top5Foods) {
            top5FoodsList.add(FoodResponseDTO.of(top5Food));
        }

        return top5FoodsList;
    }

    @Override
    public List<BookMarkResponseDTO> findByUserId(Long userId) {
        List<BookMark> bookmark = dao.findByUserId(userId);
        List<BookMarkResponseDTO> bookmarkList = new ArrayList<>();
        for (BookMark bookMark : bookmark) {
            bookmarkList.add(BookMarkResponseDTO.of(bookMark));
        }
        return bookmarkList;
    }
}

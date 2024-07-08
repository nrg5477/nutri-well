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

        if (existBookmark != null) {//해당 Bookmark Data가 존재할시 상태만 update
            int result = isPreferred ?
                    dao.updatePreferredState(existBookmark.getId(), newState) :
                    dao.updateExcludedState(existBookmark.getId(), newState);
            if (result > 0) {//업데이트 완료시
                if (isPreferred) {
                    existBookmark.setPreferredState(newState);//즐찾,제외 상태 리턴
                } else {
                    existBookmark.setExcludedState(newState);
                }
            } else {
                System.out.println(existBookmark.getId() + ": 업데이트안됨");
            }
        } else {//해당 Bookmark Data가 존재하지 않을시 insert
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

        if(bookmark == null){ //user 로그인이 안되어 있을시(DB에 등록된게 없을때) 상태 초기화
            dto.setExcludedState(false);
            dto.setPreferredState(false);
        }else{
            dto = BookMarkResponseDTO.of(dao.findByFoodIdAndUserId(foodId, userId));
        }
        return dto;
    }

    @Override
    public List<FoodResponseDTO> findTop4Foods() {
        List<Food> top5Foods = dao.findTop4Foods();
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

    @Override
    public List<FoodResponseDTO> findFoodNamesByUserId(Long userId) {
        List<Food> foodlist = dao.findFoodNamesByUserId(userId);
        List<FoodResponseDTO > fooddtolist = new ArrayList<>();
        for (Food food : foodlist) {
            fooddtolist.add(FoodResponseDTO.of(food));
        }
        return fooddtolist;
    }
}

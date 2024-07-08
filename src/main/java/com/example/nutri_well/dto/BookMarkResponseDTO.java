package com.example.nutri_well.dto;

import com.example.nutri_well.entity.BookMark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMarkResponseDTO {
    private Long userId;
    private Long foodId;
    private boolean preferredState; // 즐겨찾기 항목 상태여부
    private boolean excludedState; // 제외식품 항목 상태여부

    public static BookMarkResponseDTO of(BookMark bookMark) {
        return new BookMarkResponseDTO(bookMark.getUser().getUserId(), bookMark.getFood().getId(), bookMark.isPreferredState(), bookMark.isExcludedState());
    }
}
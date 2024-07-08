package com.example.nutri_well.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMarkRequestDTO {
    private Long foodId;
    private Long userId;
    private boolean preferredState; // 즐겨찾기 항목 상태여부
    private boolean excludedState; // 제외식품 항목 상태여부
}
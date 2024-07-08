package com.example.nutri_well.dto;

import com.example.nutri_well.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDTO {
    private Long categoryId;
    private String categoryName;

    //CategoryResponseDTO는 자식카테고리까지 스트림으로 뽑아내서 자식카테고리 안뽑으려고 만듦.
    public static CategorySummaryDTO of(Category category) {
        return new CategorySummaryDTO(category.getId(), category.getName());
    }
}
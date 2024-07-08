package com.example.nutri_well.dto;

import com.example.nutri_well.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private List<CategoryResponseDTO> childCategory;

    public static CategoryResponseDTO of(Category categories) {

        return new CategoryResponseDTO(categories.getId(), categories.getName(),
                categories.getChildrenCategory().stream()
                        .map(CategoryResponseDTO::of)
                        .collect(Collectors.toList()));
    }
}

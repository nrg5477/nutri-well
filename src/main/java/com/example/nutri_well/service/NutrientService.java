package com.example.nutri_well.service;

import com.example.nutri_well.dto.NutrientResponseDTO;
import com.example.nutri_well.entity.Nutrient;
import com.example.nutri_well.repository.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NutrientService {
    private final NutrientRepository nutrientRepository;
    private final ModelMapper modelMapper;

    public List<NutrientResponseDTO> getAllNutrients() {
        List<Nutrient> nutrients = nutrientRepository.findAll();
        return nutrients.stream()
                .map(nutrient -> modelMapper.map(nutrient, NutrientResponseDTO.class))
                .collect(Collectors.toList());
    }
}
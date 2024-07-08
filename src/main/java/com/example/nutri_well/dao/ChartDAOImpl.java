package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Chart;
import com.example.nutri_well.repository.ChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class ChartDAOImpl implements ChartDAO {
    private final ChartRepository chartRepository;

    @Override
    public Chart insert(Chart dto) {
        return chartRepository.save(dto);
    }

    @Override
    public void delete(Long userId, LocalDate startDate) {
        chartRepository.deleteByUserIdAndStartDate(userId, startDate);
    }
}
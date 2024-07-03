package com.example.nutri_well.dao;

import com.example.nutri_well.entity.Basket;
import com.example.nutri_well.entity.Food;
import com.example.nutri_well.repository.BasketRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class BasketDAOImpl implements BasketDAO {
    private final BasketRepository basketRepository;
    @Override
    public Basket insert(Basket dto) {
        return basketRepository.save(dto);
    }

    @Override
    public void delete(Long userId, LocalDate startDate) {
        basketRepository.deleteByUserIdAndStartDate(userId, startDate);
    }
}
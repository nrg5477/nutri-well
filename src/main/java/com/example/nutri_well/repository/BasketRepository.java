package com.example.nutri_well.repository;

import com.example.nutri_well.entity.Basket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Basket b WHERE b.userId.id = :userId AND b.startDate = :startDate")
    void deleteByUserIdAndStartDate(@Param("userId") Long userId, @Param("startDate") LocalDate startDate);
}

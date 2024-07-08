package com.example.nutri_well.repository;

import com.example.nutri_well.entity.FoodApprove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodApproveRepository extends JpaRepository<FoodApprove, Long> {
}
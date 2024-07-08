package com.example.nutri_well.repository;

import com.example.nutri_well.entity.BookMark;
import com.example.nutri_well.entity.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    BookMark findByFoodIdAndUser_UserId(Long foodId, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE BookMark b SET b.preferredState = :preferredState WHERE b.id = :id")
    int updatePreferredState(@Param("id") Long id, @Param("preferredState") boolean preferredState);

    @Modifying
    @Transactional
    @Query("UPDATE BookMark b SET b.excludedState = :excludedState WHERE b.id = :id")
    int updateExcludedState(@Param("id") Long id, @Param("excludedState") boolean excludedState);

    @Query("SELECT bm.food " +
            "FROM BookMark bm " +
            "GROUP BY bm.food " +
            "ORDER BY COUNT(bm.food) DESC")
    List<Food> findTop4Foods(Pageable pageable);

    List<BookMark> findByUser_UserId(Long userId);

    @Query("SELECT bm.food FROM BookMark bm WHERE bm.user.id = :userId")
    List<Food> findFoodNamesByUserId(@Param("userId") Long userId);
}

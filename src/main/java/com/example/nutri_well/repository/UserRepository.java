package com.example.nutri_well.repository;

import com.example.nutri_well.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); //이미 email을 통해 생성된 사용자인지 체크

    Optional<User> findByPassword(String password);

    Optional<User> findByTel(String tel);

    Optional<User> findByGender(String gender);

    Optional<User> findByWeight(float weight);

    Optional<User> findByHeight(float height);

    Optional<User> findByPicture(String picture);

    Optional<User> findByBaselMetabolism(Integer baselMetabolism);
}
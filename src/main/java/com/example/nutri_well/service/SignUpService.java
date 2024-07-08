package com.example.nutri_well.service;

import com.example.nutri_well.dto.SignUpDTO;
import com.example.nutri_well.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface SignUpService {
    User registerUser(SignUpDTO MemberProfile);

    Optional<User> findByemail(String email);

    Optional<User> findBypassword(String password);

    Optional<User> findBygender(String gender);

    Optional<User> findByweight(float weight);

    Optional<User> findByheight(float height);

    Optional<User> findBytel(String tel);

    Optional<User> findBypicture(String picture);
}

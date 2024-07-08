package com.example.nutri_well.service;

import com.example.nutri_well.dto.SignUpDTO;
import com.example.nutri_well.model.User;

public interface SignUpService {
    User registerUser(SignUpDTO MemberProfile);
}
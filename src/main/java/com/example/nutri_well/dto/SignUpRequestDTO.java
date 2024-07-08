package com.example.nutri_well.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
    private String username;
    private String email;
    private String gender;
    private String password;
    private Date birth;
    private String nickName;
    private float weight;
    private float height;
    private String tel;
    private String picture;
}
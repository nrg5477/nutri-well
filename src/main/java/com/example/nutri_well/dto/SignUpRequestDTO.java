package com.example.nutri_well.dto;

import jakarta.persistence.Column;
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
    @Column(nullable = false) //유니크
    private String username;

    @Column(nullable = false) //= 로그인 시 사용할 계정명
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birth;

    private String nickName;

    private float weight;

    private float height;

    private String tel;

    private String picture;
}


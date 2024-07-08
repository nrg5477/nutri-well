package com.example.nutri_well.dto;

import com.example.nutri_well.model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Getter
@Data
public class SignUpDTO {
    private String username;
    private String email;
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;
    private String weight;
    private String height;
    private String tel;
    private int baselMetabolism;
    private String picture;
    private Role role = Role.USER;
    private boolean state = true;
}

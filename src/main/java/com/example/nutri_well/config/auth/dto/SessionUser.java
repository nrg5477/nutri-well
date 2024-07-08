package com.example.nutri_well.config.auth.dto;

import com.example.nutri_well.model.Role;
import com.example.nutri_well.model.User;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * 세션에 저장될 수 있는 User 엔티티의 직렬화된 버전을 제공하는 클래스.
 * 세션에 저장될때 필요한 필드를 포함.
 * User 엔티티를 SessionUser로 변환하기 위한 생성자를 제공.
 */
@Getter
public class SessionUser implements Serializable {
    private Long userId;
    private String name;
    private String email;
    private String gender;
    private Float weight;
    private Date birth;
    private String tel;
    private boolean state;
    private Float height;
    private int baselMetabolism;
    private String password;
    private String picture;
    private Role role;

    public SessionUser(User user) {
        this.userId = user.getUserId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.weight = user.getWeight();
        this.birth = user.getBirth();
        this.tel = user.getTel();
        this.state = user.isState();
        this.height = user.getHeight();
        this.baselMetabolism = user.getBaselMetabolism();
        this.password = user.getPassword();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }
}

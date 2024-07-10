package com.example.nutri_well.config.auth.dto;

import com.example.nutri_well.model.Role;
import com.example.nutri_well.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

/**
 * OAuth2를 사용하여 소셜 로그인을 통해 얻은 사용자의 attributes를 나타내고 저장.
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String username;
    private String email;
    private String picture;
    private String gender;
    private Date birthday;
    private String mobile;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String gender, Date birthday, String mobile) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = name;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.birthday = birthday;
        this.mobile = mobile;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // 여기서 네이버 등 구분 (ofNaver...)
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .gender((String) response.get("gender"))
                .birthday((Date) response.get("birthday"))
                .mobile((String) response.get("mobile"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(response) // attributes 설정 추가
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .picture(picture)
                .role(Role.USER) // 기본 권한 USER
                .gender(gender)
                .birth(birthday)
                .tel(mobile)
                .build();
    }
}

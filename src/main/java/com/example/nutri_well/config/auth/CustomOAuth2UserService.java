package com.example.nutri_well.config.auth;

import com.example.nutri_well.config.auth.dto.OAuthAttributes;
import com.example.nutri_well.config.auth.dto.SessionUser;
import com.example.nutri_well.model.User;
import com.example.nutri_well.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * OAuth2UserServcie 의 구현.
 * 소셜 로그인 성공 후 취해야할 조치를 담당.
 * 사용자 등록, 정보 업데이트, 세션 저장등을 담당.
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); // delegate : OAuth2UserService 인터페이스를 구혈할 객체
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //request에는 토큰, 클라이언트 id 등이 담겨있음.

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); //사용자 정보를 받아서 OAuthAttributes로 전환
        if (attributes == null) {
            System.out.println("OAuthAttributes is null");
            throw new OAuth2AuthenticationException("OAuthAttributes is null");
        }
        User user = saveOrUpdate(attributes); //데이터베이스를 확인해서 기존 유저면 업데이트 , 새로운 유저면 저장
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용) => 세션에 저장
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes) {
        Optional<User> userOptional = userRepository.findByEmail(attributes.getEmail()); //이메일로 사용자가 존재하는지 확인
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.update(attributes.getUsername(), attributes.getPicture());
        } else {
            user = attributes.toEntity();
            user.setPassword("oauth2user"); // 기본 비밀번호 설정
            user.setGender(attributes.getGender() != null ? attributes.getGender() : "unknown"); //성별 설정
            user.setState(true); // 상태 기본값 설정
        }

        return userRepository.save(user);
    }
}

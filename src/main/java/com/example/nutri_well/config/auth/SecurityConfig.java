package com.example.nutri_well.config.auth;


import com.example.nutri_well.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 애플리케이션의 보안 설정을 구성.
 * 웹 보안을 활성화하고 HTTP 요청을 처리하기 위한 보안 필터 체인을 적용.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler(); //성공 핸들러 선언
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/index.do");
        http
                .csrf(csrf -> csrf.disable())  // 최신 버전에서 CSRF 비활성화 설정, spring boot 6.1 이상버전
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // h2-console 화면을 사용하기 위해 해당 옵션 disable
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/login", "/login/**", "/oauth2/**", "/**").permitAll() //해당 url은 모두 접근가능
                                .requestMatchers("/food-approve/list/**", "/f").hasRole(Role.USER.name()) //이 url은 USER만 접근가능
                                .anyRequest().authenticated()// 위의 url을 제외하고 나머지 url들은 인증된 사용자만 접근가능
                        //.anyRequest().permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/index.do") //로그아웃 성공시
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler)//성공핸들러 설정
                        .userInfoEndpoint(userInfo -> userInfo //oauth2 로그인 성공 후 가져올 때의 설정들
                                //소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                                .userService(customOAuth2UserService) //리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
                        )
                );

        return http.build();
    }
}
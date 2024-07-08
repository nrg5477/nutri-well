package com.example.nutri_well.service;

import com.example.nutri_well.dto.SignUpDTO;
import com.example.nutri_well.model.User;
import com.example.nutri_well.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User registerUser(SignUpDTO memberSignUpDTO) {
        log.debug("memberSignUpDTO={}", memberSignUpDTO);

        if (userRepository.findByEmail(memberSignUpDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }
        // null 체크
        if (memberSignUpDTO.getUsername() == null || memberSignUpDTO.getEmail() == null || memberSignUpDTO.getGender() == null || memberSignUpDTO.getPassword() == null) {
            throw new IllegalArgumentException("필수 입력 필드가 누락되었습니다");
        }

        User user = User.builder()
                .username(memberSignUpDTO.getUsername())
                .email(memberSignUpDTO.getEmail())
                .password(memberSignUpDTO.getPassword())
                .birth(memberSignUpDTO.getBirth())
                .gender(memberSignUpDTO.getGender())
                .weight(Float.parseFloat(memberSignUpDTO.getWeight()))
                .height(Float.parseFloat(memberSignUpDTO.getHeight()))
                .tel(memberSignUpDTO.getTel())
                .picture(memberSignUpDTO.getPicture())
                .baselMetabolism(memberSignUpDTO.getBaselMetabolism())
                .role(memberSignUpDTO.getRole())
                .state(true)
                .build();
        return userRepository.save(user);
    }
}
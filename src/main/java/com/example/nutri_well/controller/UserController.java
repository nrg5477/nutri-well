package com.example.nutri_well.controller;

import com.example.nutri_well.config.auth.dto.SessionUser;
import com.example.nutri_well.model.User;
import com.example.nutri_well.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        System.out.println("로그인호출");
        Optional<User> userOptional = userService.findByUserEmail(email);
        Map<String, String> response = new HashMap<>();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", new SessionUser(user)); //사용자 정보를 세션에 저장
                response.put("username", user.getUsername());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        System.out.println("로그아웃 호출");
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/checkSession")
    public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Map<String, Object> response = new HashMap<>();
        if (sessionUser != null) {
            response.put("loggedIn", true);
            response.put("username", sessionUser.getName());
        } else {
            response.put("loggedIn", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("gender") String gender, @RequestParam("height") float height,
                                @RequestParam("weight") float weight, @RequestParam("birth") String birth,
                                @RequestParam("tel") String tel, @RequestParam("picture") String picture,
                                HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if (sessionUser != null) {
            User existingUser = userService.getCurrentUser(sessionUser.getEmail());
            System.out.println("=====================user.getemail" + sessionUser.getEmail());
            existingUser.setGender(gender);
            existingUser.setHeight(height);
            existingUser.setWeight(weight);
            // Convert birth string to Date
            try {
                Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
                existingUser.setBirth(birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            existingUser.setTel(tel);
            existingUser.setPicture(picture);

            userService.updateUser(existingUser);
        }
        return "redirect:/mypage.do";
    }


}

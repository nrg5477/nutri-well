package com.example.nutri_well.controller;

import com.example.nutri_well.config.auth.dto.SessionUser;
import com.example.nutri_well.dto.FoodApproveResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.model.User;
import com.example.nutri_well.model.myCalendar;
import com.example.nutri_well.service.BookMarkService;
import com.example.nutri_well.service.FoodApproveServie;
import com.example.nutri_well.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final FoodApproveServie foodApproveService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final BookMarkService bookMarkService;

    @GetMapping("/")
    public String index(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        //src/main/resources/templates/indecs.mustache
        return "indecs";
    }

    @GetMapping("/index.do")
    public String indexHtml(Model model) {
        List<FoodResponseDTO> top4Foods = bookMarkService.findTop4Foods();
        model.addAttribute("top4Foods", top4Foods);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser != null) {
            model.addAttribute("basalMetabolism", sessionUser.getBaselMetabolism());
        } else {
            model.addAttribute("basalMetabolism", -1);
        }
        System.out.println(top4Foods);
        return "include/indexContent";
    }

    @GetMapping("/mypage.do")
    public String mypageHtml(HttpSession session, Model model, HttpServletRequest request) {
        List<FoodApproveResponseDTO> approvals = foodApproveService.getAllFoodApprovals();
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if (sessionUser == null) {
            model.addAttribute("loginError", true);
            return "redirect:" + request.getHeader("Referer"); // 이전 페이지로 리다이렉트
        }
        if (sessionUser != null) {
            Optional<User> userOptional = userService.findByUserEmail(sessionUser.getEmail());//userservice를 통해 사용자 객체 조회
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("approvals", approvals);
        return "user/mypage";
    }

    @GetMapping("/addFoodPage")
    public String showAddFoodPage() {
        return "food/addFood";
    }
}

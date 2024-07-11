package com.example.nutri_well.controller;

import com.example.nutri_well.config.auth.dto.SessionUser;
import com.example.nutri_well.dto.CategoryResponseDTO;
import com.example.nutri_well.dto.FoodApproveResponseDTO;
import com.example.nutri_well.dto.FoodResponseDTO;
import com.example.nutri_well.model.User;
import com.example.nutri_well.service.BookMarkService;
import com.example.nutri_well.service.CategoryService;
import com.example.nutri_well.service.FoodApproveService;
import com.example.nutri_well.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

/**
 * 인덱스페이지 또는 페이지이동과 관련된 기능 또는 관련된 요청을 처리하는 컨트롤러 클래스.
 */
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final FoodApproveService foodApproveService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final BookMarkService bookMarkService;
    private final CategoryService categoryService;

    @GetMapping("/index.do")
    public String indexHtml(Model model) {
        List<FoodResponseDTO> top4Foods = bookMarkService.findTop4Foods();
        List<CategoryResponseDTO> parentCategory = categoryService.findByParentCategoryIsNull();

        model.addAttribute("top4Foods", top4Foods);
        model.addAttribute("category", parentCategory);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if (sessionUser != null) {
            model.addAttribute("baselMetabolism", sessionUser.getBaselMetabolism());
        } else {
            model.addAttribute("baselMetabolism", -1);
        }

        return "include/indexContent";
    }

    @GetMapping("/mypage.do")
    public String mypageHtml(HttpSession session, Model model, HttpServletRequest request) {
        List<FoodApproveResponseDTO> approvals = foodApproveService.getAllFoodApprovals();
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if (sessionUser == null) {
            model.addAttribute("loginError", true);
            return "redirect:" + request.getHeader("Referer"); // 이전 페이지로 리다이렉트
        } else {
            Optional<User> userOptional = userService.findByUserEmail(sessionUser.getEmail()); //userservice를 통해 사용자 객체 조회
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("approvals", approvals);
        return "user/mypage";
    }
}
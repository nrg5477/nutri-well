package com.example.nutri_well.controller;

import com.example.nutri_well.dto.SignUpDTO;
import com.example.nutri_well.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 회원가입과 관련된 기능 또는 관련된 요청을 처리하는 컨트롤러 클래스.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class SignUpController {
    private final SignUpService memberService;

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("memberSignUpDTO", new SignUpDTO());
        return "signup/signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("memberSignUpDTO") SignUpDTO memberSignUpDTO, Model model, RedirectAttributes redirectAttributes) {
        try {
            memberService.registerUser(memberSignUpDTO);
            //   redirectAttributes.addFlashAttribute("signupSuccess",true);
            return "redirect:/index.do?signupSuccess=true";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("signupError", e.getMessage());
            return "redirect:/member/signup?signupSuccess=false";
        }
    }

    @GetMapping("/main")
    public String main() {
        return "index";
    }
}

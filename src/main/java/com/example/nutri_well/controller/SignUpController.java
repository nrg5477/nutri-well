package com.example.nutri_well.controller;

import com.example.nutri_well.dto.SignUpDTO;
import com.example.nutri_well.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//뷰에서 요청 -> 요청을 받아주는 역활
//서비스에 있는 메소드를 부르면서 연결
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class SignUpController {

    private final SignUpService memberService;

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("memberSignUpDTO",new SignUpDTO());
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
    public String main(){

        return "index";
    }
}

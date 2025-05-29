package com.office.calendar.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    /*
    회원 가입 양식
     */
    @GetMapping("/member/signup")
    public String signUp() {
        System.out.println("[MemberController] signUp()");

        String nextPage = "member/signup_form";
        return nextPage;
    }

    /*
    회원 가입 확인
     */
    @PostMapping("/member/signup_confirm")
    public String signupConfirm(MemberDto memberDto, Model model) {
        System.out.println("[MemberController] signupConfirm()");

        String nextPage = "member/signup_result";

        int result = memberService.signupConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
    로그인 양식
     */
    @GetMapping("/member/signin")
    public String signIn() {
        System.out.println("[MemberController] signIn()");

        String nextPage = "member/signin_form";
        return nextPage;
    }

    /*
    로그인 확인
     */
    @PostMapping("/member/signin_confirm")
    public String signInConfirm(MemberDto memberDto, Model model, HttpSession session) {
        System.out.println("[MemberController] signInConfirm()");

        String nextPage = "member/signin_result";

        String loginedID = memberService.signInConfirm(memberDto);
        model.addAttribute("loginedID", loginedID);

        if (loginedID != null) {
            session.setAttribute("loginedID", loginedID);
            session.setMaxInactiveInterval(60 * 30);
        }

        return nextPage;
    }

    /*
    로그아웃 확인
     */
    @GetMapping("/member/signout_confirm")
    public String signOutConfirm(HttpSession session) {
        System.out.println("[MemberController] signOutConfirm()");

        String nextPage = "redirect:/";
        session.invalidate();
        return nextPage;
    }
}

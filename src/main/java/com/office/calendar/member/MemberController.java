package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String signupConfirm(MemberDto memberDto) {
        System.out.println("[MemberController] signupConfirm()");

        String nextPage = "member/signup_result";

        int result = memberService.signupConfirm(memberDto);

        return nextPage;
    }
}

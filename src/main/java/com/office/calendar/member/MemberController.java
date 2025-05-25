package com.office.calendar.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/member/signup")
    public String signUp() {
        System.out.println("[MemberController] signUp()");

        String nextPage = "member/signup_form";
        return nextPage;
    }
}

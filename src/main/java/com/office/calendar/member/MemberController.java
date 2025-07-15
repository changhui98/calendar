package com.office.calendar.member;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
        회원 가입 양식
         */
    @GetMapping("/signup")
    public String signUp() {
        log.info("signup()");

        String nextPage = "member/signup_form";
        return nextPage;
    }

    /*
    회원 가입 확인
     */
    @PostMapping("/signup_confirm")
    public String signupConfirm(MemberDto memberDto, Model model) {
        log.info("signup_confirm()");

        String nextPage = "member/signup_result";

        int result = memberService.signupConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
    로그인 양식
     */
    @GetMapping("/signin")
    public String signIn() {
        log.info("signin()");

        String nextPage = "member/signin_form";
        return nextPage;
    }

    /*
    로그인 확인

    @PostMapping("/signin_confirm")
    public String signInConfirm(MemberDto memberDto, Model model, HttpSession session) {
        log.info("signin_confirm()");

        String nextPage = "member/signin_result";

        String loginedID = memberService.signInConfirm(memberDto);
        model.addAttribute("loginedID", loginedID);

        if (loginedID != null) {
            session.setAttribute("loginedID", loginedID);
            session.setMaxInactiveInterval(60 * 30);
        }

        return nextPage;
    }
     */

    /*
    로그아웃 확인

    @GetMapping("/signout_confirm")
    public String signOutConfirm(HttpSession session) {
        log.info("signout_confirm()");

        String nextPage = "redirect:/";
        session.invalidate();
        return nextPage;
    }
     */

    /*
    계정 정보 수정 양식
     */
    @GetMapping("/modify")
    public String modify(HttpSession session, Model model, Principal principal) {
        log.info("modify()");

        String nextPage = "member/modify_form";


        //String loginedID = String.valueOf(session.getAttribute("loginedID"));
        MemberDto loginedMemberDto = memberService.modify(principal.getName());
        model.addAttribute("loginedMemberDto", loginedMemberDto);

        return nextPage;
    }

    /*
    계정 정보 수정 확인
     */
    @PostMapping("/modify_confirm")
    public String modifyConfirm(MemberDto memberDto, Model model) {
        log.info("modify_confirm()");

        String nextPage = "member/modify_result";
        int result = memberService.modifyConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;
    }

    /*
    비밀번호 찾기 양식
     */
    @GetMapping("/findpassword")
    public String findPassword() {
        log.info("findpassword");

        String nextPage = "member/findpassword_form";

        return nextPage;
    }

    /*
    비밀번호 찾기 확인
     */
    @PostMapping("/findpassword_confirm")
    public String findPasswordConfirm(MemberDto memberDto, Model model) {
        log.info("findpassword_confirm()");

        String nextPage = "member/findpassword_result";

        int result = memberService.findpasswordConfirm(memberDto);
        model.addAttribute("result", result);

        return nextPage;
    }

    // 로그인 결과
    @GetMapping("/signin_result")
    public String signinResult(@RequestParam(value = "loginedID", required = false) String loginedID, Model model) {
        log.info("signinResult()");

        String nextPage = "member/signin_result";
        model.addAttribute("loginedID", loginedID);

        return nextPage;
    }


}

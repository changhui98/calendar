package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public final static int USER_ID_ALREADY_EXIST   = 0;
    public final static int USER_SIGNUP_SUCCESS     = 1;
    public final static int USER_SIGNUP_FAIL        = -1;

    @Autowired
    MemberDao memberDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public int signupConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] signupConfirm()");

        boolean isMember = memberDao.isMember(memberDto.getId());

        if (!isMember) {
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            int result = memberDao.insertMember(memberDto);
            if (result > 0) {
                return USER_SIGNUP_SUCCESS;
            } else {
                return USER_SIGNUP_FAIL;
            }
        } else {
            return USER_ID_ALREADY_EXIST;
        }
    }

    public String signInConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] signInConfirm()");

        MemberDto dto = memberDao.selectMemberByID(memberDto.getId()); //MemberDto or null
        if (dto != null && passwordEncoder.matches(memberDto.getPw(), dto.getPw())) {
            System.out.println("[MemberService] MEMBER LOGIN SUCCESS!!");
            return dto.getId();
        } else {
            System.out.println("[MemberService] MEMBER LOGIN FAIL!! ");
            return null;
        }

    }

    public MemberDto modify(String loginedID) {
        System.out.println("[MemberService] modify()");

        return memberDao.selectMemberByID(loginedID);
    }

    public int modifyConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] modifyConfirm()");

        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

        return memberDao.updateMember(memberDto);
    }
}

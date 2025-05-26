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
}

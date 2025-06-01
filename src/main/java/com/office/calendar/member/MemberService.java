package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class MemberService {

    public final static int USER_ID_ALREADY_EXIST   = 0;
    public final static int USER_SIGNUP_SUCCESS     = 1;
    public final static int USER_SIGNUP_FAIL        = -1;

    @Autowired
    MemberDao memberDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

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

    public int findpasswordConfirm(MemberDto memberDto) {
        System.out.println("[MemberService] findpasswordConfirm()");

        //1. 인증 by ID, MAIL
        MemberDto selectedMemberDto = memberDao.selectMemberByIDAndMail(memberDto);
        int result = 0;

        if (selectedMemberDto != null) {
            //2. 새 비밀번호 생성
            String newPassword = createNewPassword();

            //3. DB 업데이트
            result = memberDao.updatePassword(memberDto.getId(), passwordEncoder.encode(newPassword));
            if (result > 0) {

                //4. 새 비밀번호 메일 발송
                sendNewPasswordByMail(memberDto.getMail(), newPassword);

            }

        }
        return result;
    }

    private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
        System.out.println("[MemberService] sendNewPasswordByMail");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toMailAddr);
        simpleMailMessage.setSubject("[MyCalendar] 새 비밀번호 안내입니다.");
        simpleMailMessage.setText("새 비밀번호 : " + newPassword);
        simpleMailMessage.setFrom("yuch0102@gmail.com");

        javaMailSender.send(simpleMailMessage);



    }

    private String createNewPassword() {
        System.out.println("[MemberService] createNewPassword()");

        char[] chars = new char[]{

                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'm', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer stringBuffer = new StringBuffer();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());

        int index = 0;
        int length = chars.length;
        for (int i = 0; i < 8; i++) {
            index = secureRandom.nextInt(length);

            if (index % 2 == 0) {
                stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
            } else {
                stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
            }

        }
        System.out.println("[MemberService] NEW PASSWORD : " +stringBuffer.toString());

        return stringBuffer.toString();
    }
}

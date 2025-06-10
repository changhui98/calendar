package com.office.calendar.member;

import com.office.calendar.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@Service
public class MemberService {

    private final MemberDao memberDao;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

    private final MemberMapper memberMapper;

    public final static int USER_ID_ALREADY_EXIST   = 0;
    public final static int USER_SIGNUP_SUCCESS     = 1;
    public final static int USER_SIGNUP_FAIL        = -1;


    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, MemberMapper memberMapper) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.memberMapper = memberMapper;
    }

    public int signupConfirm(MemberDto memberDto) {
        log.info("signupConfirm()");

        boolean isMember = memberMapper.isMember(memberDto.getId());

        if (!isMember) {
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            int result = memberMapper.insertMember(memberDto);
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
        log.info("signInConfirm()");

        MemberDto dto = memberMapper.selectMemberByID(memberDto.getId()); //MemberDto or null
        if (dto != null && passwordEncoder.matches(memberDto.getPw(), dto.getPw())) {
            log.info("MEMBER LOGIN SUCCESS!!");
            return dto.getId();
        } else {
            log.info("MEMBER LOGIN FAIL!!");
            return null;
        }

    }

    public MemberDto modify(String loginedID) {
        log.info("modify()");

        return memberMapper.selectMemberByID(loginedID);
    }

    public int modifyConfirm(MemberDto memberDto) {
        log.info("modifyConfirm()");

        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

        return memberMapper.updateMember(memberDto);
    }

    public int findpasswordConfirm(MemberDto memberDto) {
        log.info("findpasswordConfirm()");

        //1. 인증 by ID, MAIL
        MemberDto selectedMemberDto = memberMapper.selectMemberByIDAndMail(memberDto);
        int result = 0;

        if (selectedMemberDto != null) {
            //2. 새 비밀번호 생성
            String newPassword = createNewPassword();

            //3. DB 업데이트
            result = memberMapper.updatePassword(memberDto.getId(), passwordEncoder.encode(newPassword));
            if (result > 0) {

                //4. 새 비밀번호 메일 발송
                sendNewPasswordByMail(memberDto.getMail(), newPassword);

            }

        }
        return result;
    }

    private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
        log.info("sendNewPasswordByMail()");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toMailAddr);
        simpleMailMessage.setSubject("[MyCalendar] 새 비밀번호 안내입니다.");
        simpleMailMessage.setText("새 비밀번호 : " + newPassword);
        simpleMailMessage.setFrom("yuch0102@gmail.com");

        javaMailSender.send(simpleMailMessage);



    }

    private String createNewPassword() {
        log.info("createNewPassword()");

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
        log.info("NEW PASSWORD: {}", stringBuffer.toString());

        return stringBuffer.toString();
    }
}

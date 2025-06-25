package com.office.calendar.member;

import com.office.calendar.member.jpa.MemberEntity;
import com.office.calendar.member.jpa.MemberRepository;
import com.office.calendar.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

    private final MemberRepository memberRepository;

    public final static int USER_ID_ALREADY_EXIST   = 0;
    public final static int USER_SIGNUP_SUCCESS     = 1;
    public final static int USER_SIGNUP_FAIL        = -1;

    public final static int MODIFY_SUCCESS  = 1;
    public final static int MODIFY_FAIL     = 0;

    public final static int NEW_PASSWORD_CREATION_SUCCESS   = 1;
    public final static int NEW_PASSWORD_CREATION_FAIL      = 0;


    public MemberService(PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.memberRepository = memberRepository;

    }

    @Transactional
    public int signupConfirm(MemberDto memberDto) {
        log.info("signupConfirm()");

        boolean isMember = memberRepository.existsByMemId(memberDto.getId());

        if (!isMember) {
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

            MemberEntity savedMemberEntity = memberRepository.save(memberDto.toEntity());
            if (savedMemberEntity != null) {
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

        Optional<MemberEntity> optionalMember = memberRepository.findByMemId(memberDto.getId());
        if (optionalMember.isPresent() && passwordEncoder.matches(memberDto.getPw(), optionalMember.get().getMemPw())){
            log.info("MEMBER LOGIN SUCCESS!!");
            return optionalMember.get().getMemId();
        } else {
            log.info("MEMBER LOGIN FAIL!!");
            return null;
        }
    }

    public MemberDto modify(String loginedID) {
        log.info("modify()");

        Optional<MemberEntity> optionalMember = memberRepository.findByMemId(loginedID);
        if (optionalMember.isPresent()) {
            MemberEntity memberEntity = optionalMember.get();
            return memberEntity.toDto();
        }
        return null;
    }

    @Transactional
    public int modifyConfirm(MemberDto memberDto) {
        log.info("modifyConfirm()");

        memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));

        Optional<MemberEntity> optionalMember = memberRepository.findById(memberDto.getNo());
        if (optionalMember.isPresent()) {
            MemberEntity findedMemberEntity = optionalMember.get();
            findedMemberEntity.setMemPw(memberDto.getPw());
            findedMemberEntity.setMemMail(memberDto.getMail());
            findedMemberEntity.setMemPhone(memberDto.getPhone());
            findedMemberEntity.setMemMod_date(LocalDateTime.now());

            // memberRepository.save(findedMemberEntity); Transactional 적용되어 있으면 save()호출하지 않아도 자동 반영

            return MODIFY_SUCCESS;
        }
        return MODIFY_FAIL;
    }

    @Transactional
    public int findpasswordConfirm(MemberDto memberDto) {
        log.info("findpasswordConfirm()");

        Optional<MemberEntity> optionalMember = memberRepository.findByMemIdAndMemMail(memberDto.getId(), memberDto.getMail());
        if (optionalMember.isPresent()) {
            String newPassword = createNewPassword();
            MemberEntity memberEntity = optionalMember.get();
            memberEntity.setMemPw(passwordEncoder.encode(newPassword));

            MemberEntity updateMember = memberRepository.save(memberEntity);
            if (updateMember != null)
                sendNewPasswordByMail(memberDto.getMail(), newPassword);

            return NEW_PASSWORD_CREATION_SUCCESS;
        }

        return NEW_PASSWORD_CREATION_FAIL;
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

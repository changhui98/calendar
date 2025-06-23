package com.office.calendar.member;

import com.office.calendar.member.jpa.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto implements Serializable {    // Serializable 인터페이스 구현

    // 직렬화 버전 ID
    private static final long serialVersionUID = 1L;

    private int no;                 //사용자 고유 번호
    private String id;              //사용자 ID
    private String pw;              //사용자 PW
    private String mail;            //사용자 이메일
    private String phone;           //사용자 연락처
    private int authority_no;       //사용자 권한 번호
    private String reg_date;        //사용자 정보 등록일
    private String mod_date;        //사용자 정보 수정일


    public MemberEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return MemberEntity.builder()
                .memNo(no)
                .memId(id)
                .memPw(pw)
                .memMail(mail)
                .memPhone(phone)
                .memAuthority_no(authority_no)
                .memReg_date(reg_date != null ? LocalDateTime.parse(reg_date, formatter) : null)
                .memMod_date(mod_date != null ? LocalDateTime.parse(mod_date, formatter) : null)
                .build();
    }
}

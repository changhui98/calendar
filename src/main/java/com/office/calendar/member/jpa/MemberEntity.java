package com.office.calendar.member.jpa;

import com.office.calendar.member.MemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "USER_MEMBER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @Column(name = "NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memNo;

    @Column(name = "ID", nullable = false, length = 20)
    private String memId;

    @Column(name = "PW", nullable = false, length = 100)
    private String memPw;

    @Column(name = "MAIL", nullable = false, length = 20)
    private String memMail;

    @Column(name = "PHONE", nullable = false, length = 20)
    private String memPhone;

    @Column(name = "AUTHORITY_NO")
    private int memAuthority_no;

    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime memReg_date;

    @Column(name = "MOD_DATE")
    private LocalDateTime memMod_date;

    @PrePersist
    protected void onCreate() {
        this.memAuthority_no =1;
        this.memReg_date = LocalDateTime.now();
        this.memMod_date = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.memMod_date = LocalDateTime.now();
    }

    public MemberDto toDto() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return MemberDto.builder()
                .no(memNo)
                .id(memId)
                .pw(memPw)
                .mail(memMail)
                .phone(memPhone)
                .authority_no(memAuthority_no)
                .reg_date(memReg_date != null ? memReg_date.format(formatter) : null)
                .mod_date(memMod_date != null ? memMod_date.format(formatter) : null)
                .build();
    }

}

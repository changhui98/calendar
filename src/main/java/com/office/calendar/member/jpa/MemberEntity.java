package com.office.calendar.member.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_MEMBER")
@Data
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

}

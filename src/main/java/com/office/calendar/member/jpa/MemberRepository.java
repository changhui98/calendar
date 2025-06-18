package com.office.calendar.member.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    /*
    회원 ID로 중복 체크
     */
    public boolean existsByMemId(String memId);

    /*
    회원 ID로 회원 조회
     */
    public Optional<MemberEntity> findByMemId(String memId);

    /*
    회원 Id와 이메일로 회원 조회
     */
    public Optional<MemberEntity> findByMemIdAndMemMail(String memId, String memMail);
}

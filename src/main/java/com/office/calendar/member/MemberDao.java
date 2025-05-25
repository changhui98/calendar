package com.office.calendar.member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    public boolean isMember(String id) {
        System.out.println("[MemberDao] isMember()");



        return false;
    }

    public int insertMember(MemberDto memberDto) {
        System.out.println("[MemberDao] insertMember()");



        return 0;
    }
}

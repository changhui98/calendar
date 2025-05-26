package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean isMember(String id) {
        System.out.println("[MemberDao] isMember()");

        String sql = "SELECT COUNT(*) FROM USER_MEMBER WHERE ID = ?";

        int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (result > 0) {
            return true;
        }

        return false;
        }


    public int insertMember(MemberDto memberDto) {
        System.out.println("[MemberDao] insertMember()");

        String sql = "INSERT INTO USER_MEMBER(ID, PW, MAIL, PHONE) VALUES(?, ?, ?, ?)";

        int result = -1;

        try {
            result = jdbcTemplate.update(sql, memberDto.getId(), memberDto.getPw(), memberDto.getMail(), memberDto.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}


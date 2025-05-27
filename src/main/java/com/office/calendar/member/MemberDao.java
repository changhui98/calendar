package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public MemberDto selectMemberByID(String id) {
        System.out.println("[MemberDao] selectMemberByID()");

        String sql = "SELECT * FROM USER_MEMBER WHERE ID = ?";

        List<MemberDto> memberDtos = new ArrayList<>();

        try {
            memberDtos = jdbcTemplate.query(sql, new RowMapper<MemberDto>() {

                @Override
                public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                    MemberDto memberDto = new MemberDto();
                    memberDto.setNo(rs.getInt("NO"));
                    memberDto.setId(rs.getString("ID"));
                    memberDto.setPw(rs.getString("PW"));
                    memberDto.setMail(rs.getString("MAIL"));
                    memberDto.setPhone(rs.getString("PHONE"));
                    memberDto.setReg_date(rs.getString("REG_DATE"));
                    memberDto.setMod_date(rs.getString("MOD_DATE"));

                    return memberDto;
                }
            }, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberDtos.size() > 0 ? memberDtos.get(0) : null;

    }
}


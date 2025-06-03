package com.office.calendar.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class MemberDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isMember(String id) {
        log.info("isMember()");

        String sql = "SELECT COUNT(*) FROM USER_MEMBER WHERE ID = ?";

        int result = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (result > 0) {
            return true;
        }

        return false;
        }


    public int insertMember(MemberDto memberDto) {
        log.info("insertMember()");

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
        log.info("selectMemberByID()");

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

    public int updateMember(MemberDto memberDto) {
        log.info("updateMember()");

        String sql = "UPDATE USER_MEMBER SET PW = ?, MAIL = ?, PHONE = ?, MOD_DATE = NOW() WHERE NO = ?";

        int result = -1;

        try {

            result = jdbcTemplate.update(sql, memberDto.getPw(), memberDto.getMail(), memberDto.getPhone(), memberDto.getNo());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public MemberDto selectMemberByIDAndMail(MemberDto memberDto) {
        log.info("selectMemberByIDAndMail()");

        String sql = "SELECT * FROM USER_MEMBER WHERE ID =? AND MAIL = ?";

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

                    return memberDto;
                }
            }, memberDto.getId(), memberDto.getMail());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberDtos.size() > 0 ? memberDtos.get(0) : null;
    }

    public int updatePassword(String id, String encodeNewPw) {
        log.info("updatePassword()");

        String sql = "UPDATE USER_MEMBER SET PW = ?, MOD_DATE = NOW() WHERE ID = ?";

        int result = -1;
        try {

            result = jdbcTemplate.update(sql, encodeNewPw, id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}


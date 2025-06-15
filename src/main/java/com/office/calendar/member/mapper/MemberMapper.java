package com.office.calendar.member.mapper;

import com.office.calendar.member.MemberDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

//    @Select("""
//            SELECT
//                COUNT(*)
//            FROM
//                USER_MEMBER
//            WHERE
//                ID = #{id}
//            """)
    public boolean isMember(String id);

//    @Insert("INSERT INTO USER_MEMBER(ID, PW, MAIL, PHONE) VALUES(#{id}, #{pw}, #{mail}, #{phone}")
    public int insertMember(MemberDto memberdto);

//    @Select("SELECT * FROM USER_MEMBER WHERE ID = #{id}")
    public MemberDto selectMemberByID(String id);

//    @Update("UPDATE USER_MEMBER SET PW = #{pw}, MAIL = #{mail}, MOD_DATE = NOW() WHERE NO = #{no}")
    public int updateMember(MemberDto memberdto);

//    @Select("SELECT * FROM USER_MEMBER WHERE ID = #{id} AND MAIL = #{mail}")
    public MemberDto selectMemberByIDAndMail(MemberDto memberDto);

//    @Update("UPDATE USER_MEMBER SET PW = #{memPw}, MOD_DATE = NOW() WHERE ID = #{memId}")
    public int updatePassword(@Param("memId") String id,@Param("memPw") String encodeNewPW);

}

package com.office.calendar.member.mapper;

import com.office.calendar.member.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    public boolean isMember(String id);

    public int insertMember(MemberDto memberdto);

    public MemberDto selectMemberByID(String id);

    public int updateMember(MemberDto memberdto);

    public MemberDto selectMemberByIDAndMail(MemberDto memberDto);

    public int updatePassword(@Param("memId") String id,@Param("memPw") String encodeNewPW);

}

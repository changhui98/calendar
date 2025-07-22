package com.office.calendar.member.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {

    private int no;             // 권한 고유 번호
    private String role_name;   // 권한 이름

    public AuthorityEntity toEntity() {
        return AuthorityEntity.builder()
                .authNo((byte)no)
                .authRoleName(role_name)
                .build();
    }

}

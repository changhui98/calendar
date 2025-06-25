package com.office.calendar.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_AUTHORITY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityEntity {

    @Id
    @Column(name = "NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte authNo;                        // 권한 고유 번호

    @Column(name = "ROLE_NAME", nullable = false, length = 20)
    private String authRollName;                // 권한 이름

}

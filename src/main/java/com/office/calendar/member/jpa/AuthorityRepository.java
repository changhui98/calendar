package com.office.calendar.member.jpa;

import com.office.calendar.member.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Byte> {

    public boolean existsByAuthRoleName(String authRoleName);

}

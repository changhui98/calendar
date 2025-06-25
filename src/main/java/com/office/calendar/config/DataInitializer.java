package com.office.calendar.config;

import com.office.calendar.member.AuthorityEntity;
import com.office.calendar.member.jpa.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //처음 실행할 때 스프링 IoC 컨테이너에 설정 역할
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AuthorityRepository authorityRepository) {

        return args -> {

            if (!authorityRepository.existsByAuthRoleName("PRE_USER")) {
                authorityRepository.save(AuthorityEntity.builder()
                        .authRoleName("PRE_USER")
                        .build());
            }

            if (!authorityRepository.existsByAuthRoleName("USER")) {
                authorityRepository.save(AuthorityEntity.builder()
                        .authRoleName("USER")
                        .build());
            }

        };
    }
}

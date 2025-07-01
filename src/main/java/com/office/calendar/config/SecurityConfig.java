package com.office.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth.requestMatchers(
            "/",
                "/css/*",
                "/img/*",
                "/js/*",
                "/member/signup",
                "/member/signup_confirm",
                "/member/signin",
                "/member/findpassword",
                "/member/findpassword_confirm"
        ).permitAll().anyRequest().authenticated());

        http.formLogin(login -> login.disable());

        return http.build();
    }

}

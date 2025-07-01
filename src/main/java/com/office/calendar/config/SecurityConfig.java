package com.office.calendar.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
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

        http.formLogin(login -> login
                        .loginPage("/member/signin")
                        .loginProcessingUrl("/member/signin_confirm")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .successHandler((request, response, authentication) -> {
                            log.info("signin success Handler()");

                            User user =(User) authentication.getPrincipal();
                            String targetURI = "/member/signin_result?loginedID="+ user.getUsername();

                            response.sendRedirect(targetURI);
                        })
                        .failureHandler((request, response, exception) -> {
                            log.info("signin failure Handler()");

                            String targetURI = "/member/signin_result";
                            response.sendRedirect(targetURI);
                        })
                );

        return http.build();
    }

}

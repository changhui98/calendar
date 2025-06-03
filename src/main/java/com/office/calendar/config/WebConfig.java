package com.office.calendar.config;

import com.office.calendar.member.MemberSigninInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private final MemberSigninInterceptor memberSigninInterceptor;

    public WebConfig(MemberSigninInterceptor memberSigninInterceptor) {
        this.memberSigninInterceptor = memberSigninInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("addInterceptors");

        registry.addInterceptor(memberSigninInterceptor)
                .addPathPatterns("/member/modify")
                .excludePathPatterns("/member/signup", "/member/signin", "/member/signout_confirm", "/member/findpassword");

    }
}

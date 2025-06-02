package com.office.calendar.config;

import com.office.calendar.member.MemberSigninInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    MemberSigninInterceptor memberSigninInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("[WebConfig] addInterceptors");

        registry.addInterceptor(memberSigninInterceptor)
                .addPathPatterns("/member/modify")
                .excludePathPatterns("/member/signup", "/member/signin", "/member/signout_confirm", "/member/findpassword");

    }
}

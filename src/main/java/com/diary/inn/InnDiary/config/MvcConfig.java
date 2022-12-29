package com.diary.inn.InnDiary.config;

import com.diary.inn.InnDiary.utils.login.session.LoginSessionArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final LoginSessionArgumentResolver loginSessionArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginSessionArgumentResolver);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String ACCOUNT = "account";
        registry.addViewController("/" + ACCOUNT).setViewName(ACCOUNT);
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String STATIC = "/static/";
        registry
                .addResourceHandler(STATIC + "**")
                .addResourceLocations("classpath:" + STATIC + "assets");
    }
}

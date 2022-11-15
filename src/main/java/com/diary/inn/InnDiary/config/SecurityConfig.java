package com.diary.inn.InnDiary.config;

import com.diary.inn.InnDiary.utils.login.handler.OAuth2SuccessHandler;
import com.diary.inn.InnDiary.service.login.CustomOAuth2UsersService;
import com.diary.inn.InnDiary.utils.attr.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UsersService customOAuth2UserService;

    @Bean
    public AuthenticationSuccessHandler editAuthenticationSuccessHandler() {
        return new OAuth2SuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String ACCOUNT = "/account";

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/", "/account/**", "/oauth/**").permitAll()
                .antMatchers("/spaces/**").permitAll()
                .antMatchers("/api/**")
                .hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(ACCOUNT)
                .and()
                .logout()
                .logoutSuccessUrl(ACCOUNT)
                .clearAuthentication(true)
                .and()
                .oauth2Login()
                .successHandler(editAuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}

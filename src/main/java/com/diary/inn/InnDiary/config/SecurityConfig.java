package com.diary.inn.InnDiary.config;

import com.diary.inn.InnDiary.login.handler.OAuth2SuccessHandler;
import com.diary.inn.InnDiary.login.service.CustomOAuth2UsersService;
import com.diary.inn.InnDiary.attri.Role;
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
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/home/**").authenticated()
                .antMatchers("/", "/oauth/**").permitAll()
                .antMatchers("/api/v1/**", "/api/v2/**")
                .hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/oauth")
                .and()
                .logout()
                .logoutSuccessUrl("/oauth")
                .clearAuthentication(true)
                .and()
                .oauth2Login()
                .successHandler(editAuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}

package com.diary.inn.InnDiary.login.service;

import com.diary.inn.InnDiary.attri.Role;
import com.diary.inn.InnDiary.login.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String uid;
    private int company;

    private static final int GOOGLE_COM = 0;
    private static final int KAKAO_COM = 1;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String uid, int company) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.company = company;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String uid) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(attributes, uid);
        }

        return ofGoogle(userNameAttributeName, attributes, uid);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String uid) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .uid(uid)
                .company(GOOGLE_COM)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes, String uid) {
        String userNameAttributeName = "id";

        @SuppressWarnings("unchecked") Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        @SuppressWarnings("unchecked") Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        log.info("pro : {}, value : {}", attributes.get("properties"), attributes.get("properties"));
        log.info("acount : {}", kakaoAccount);

        String nickname = (String) properties.get("nickname");
        String email = (String) kakaoAccount.get("email");

        return OAuthAttributes.builder()
                .name(nickname)
                .email(email)
                .uid(uid)
                .company(KAKAO_COM)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .uid(uid)
                .company(company)
                .role(Role.USER)
                .build();
    }

    public UserEntity toAdminEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .uid(uid)
                .company(company)
                .role(Role.ADMIN)
                .build();
    }
}

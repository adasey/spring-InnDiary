package com.diary.inn.InnDiary.login.oauth2;

import com.diary.inn.InnDiary.login.user.entity.Role;
import com.diary.inn.InnDiary.login.user.entity.UserEntity;
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
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        String userNameAttributeName = "id";

        @SuppressWarnings("unchecked") Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        @SuppressWarnings("unchecked") Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        log.info("pro : {}, value : {}", attributes.get("properties"), attributes.get("properties"));
        log.info("acount : {}", kakaoAccount);

        String nickname = (String) properties.get("nickname");
        String image = (String) properties.get("thumbnail_image");

        String email = (String) kakaoAccount.get("email");

        return OAuthAttributes.builder()
                .name(nickname)
                .email(email)
                .picture(image)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }

    public UserEntity toAdminEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.ADMIN)
                .build();
    }
}

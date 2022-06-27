package com.diary.inn.InnDiray.oauth.info.impl;

import com.diary.inn.InnDiray.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }
}

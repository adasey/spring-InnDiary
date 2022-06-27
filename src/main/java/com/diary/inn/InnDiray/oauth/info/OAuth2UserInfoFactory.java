package com.diary.inn.InnDiray.oauth.info;

import com.diary.inn.InnDiray.oauth.info.impl.GoogleOAuth2UserInfo;
import com.diary.inn.InnDiray.oauth.info.impl.KakaoOAuth2UserInfo;
import com.diary.inn.InnDiray.user.domain.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}

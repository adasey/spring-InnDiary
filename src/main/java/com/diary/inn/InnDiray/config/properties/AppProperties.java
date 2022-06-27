package com.diary.inn.InnDiray.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oAuth2 = new OAuth2();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiry;
        private long refreshTokenExpiry;
    }

    public static final class OAuth2 {
        private List<String> authorisedRedirectUris = new ArrayList<>();

        public List<String> getAuthorisedRedirectUris() {
            return authorisedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorisedRedirectUris) {
            this.authorisedRedirectUris = authorisedRedirectUris;
            return this;
        }
    }
}

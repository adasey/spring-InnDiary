package com.diary.inn.InnDiray.oauth.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("Failed to generate Token. please check the log");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}

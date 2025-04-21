package com.own.identity_service.service.oauth2;

public class Constant {
    private Constant() {
        throw new IllegalStateException("Utility class");
    }
    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CODE_CHALLENGE_METHOD = "code_challenge_method";
    public static final String SCOPE = "scope";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String STATE = "state";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CODE = "code";
    public static final String CODE_VERIFIER = "code_verifier";
    public static final String ID_TOKEN = "id_token";
}

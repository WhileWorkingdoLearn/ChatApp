package com.chatapp.Services;

import com.auth0.jwt.JWTVerifier;

public interface IAuthService {
        public JWTVerifier getVerifier();
}

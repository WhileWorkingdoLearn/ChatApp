package com.chatapp.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.lang.IllegalArgumentException;

public class AuthService implements IAuthService  {

    private final JWTVerifier verifier;

    public AuthService(Algorithm algorithm){
        if(algorithm == null) throw new IllegalArgumentException("allgorithm needs to be defined");
        this.verifier = JWT.require(algorithm).build();
    }

    public JWTVerifier getVerifier(){
        return  this.verifier;
    }

}

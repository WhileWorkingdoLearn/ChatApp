package com.chatapp.Controllers;


import io.javalin.http.Context;
import javalinjwt.JavalinJWT;
import kotlin.NotImplementedError;

import java.lang.IllegalArgumentException;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatapp.ISessionContainer;
import com.chatapp.Services.IAuthService;
import com.chatapp.Tokens.WbeSocketToken;

import java.util.Optional;

import org.slf4j.event.KeyValuePair;

import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class RestController {

    private final IAuthService authService;

    private final ISessionContainer sessionContainer;

    public RestController(IAuthService authService,ISessionContainer sessionContainer){
        if(authService == null) throw new IllegalArgumentException("authService needs to be defined");
        if(sessionContainer == null) throw new IllegalArgumentException("sessionContainer needs to be defined");
        this.authService = authService;
        this.sessionContainer = sessionContainer;
    }

    public boolean isValid(){
        return true;
    }

    public void postUser(Context ctx){
         Optional<DecodedJWT> decodedJWT = JavalinJWT
            .getTokenFromHeader(ctx)
            .flatMap(val -> Optional.of(this.authService.getVerifier().verify(val)));
            
        if(!decodedJWT.isPresent()){
            ctx.status(401).result("Missing or invalid token");
         } else {
            System.out.println("" + decodedJWT.get().getClaims().values());
            var sessionId = UUID.randomUUID().toString();
            if(/*check if user is valid */true){
                sessionContainer.addSession(new KeyValuePair(sessionId, decodedJWT.get().getClaim("userID")));
            }
            LocalDateTime utcTimestamp = LocalDateTime.now(ZoneId.of("UTC"));
            ctx.status(200).json(new WbeSocketToken(sessionId,utcTimestamp.toString(),utcTimestamp.plusMinutes(5).toString()));
         }
    };

    public void getUser(Context ctx){
            throw new NotImplementedError("Meothod not yet implemented");
    }
    
}

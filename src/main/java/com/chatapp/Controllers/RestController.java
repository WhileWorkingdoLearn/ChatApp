package com.chatapp.Controllers;


import io.javalin.http.Context;
import javalinjwt.JavalinJWT;
import kotlin.NotImplementedError;

import java.lang.IllegalArgumentException;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatapp.Services.IAuthService;
import com.chatapp.Services.ISessionController;

import java.util.Optional;



public class RestController {

    private final IAuthService authService;

    private final ISessionController sessionController;

    public RestController(IAuthService authService,ISessionController sessionController){
        if(authService == null) throw new IllegalArgumentException("authService needs to be defined");
       
        if(sessionController == null) throw new IllegalArgumentException("sessionContainer needs to be defined");
        this.authService = authService;
        this.sessionController = sessionController;
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
            var userID = decodedJWT.get().getClaim("userID").asString();
            var userRole = decodedJWT.get().getClaim("role").asString();

            if(/*check if user is valid */true){
                ctx.status(200).json(sessionController.generateWebsocketToken());
            }
        }
    }



    public void getUser(Context ctx){
            throw new NotImplementedError("Meothod not yet implemented");
    }
    
}

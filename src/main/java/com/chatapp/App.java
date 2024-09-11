package com.chatapp;

import java.time.LocalDateTime;

import com.auth0.jwt.algorithms.Algorithm;
import com.chatapp.Controllers.RestController;
import com.chatapp.Controllers.WebsocketController;
import com.chatapp.Controllers.Cache.AChacheManager;
import com.chatapp.Controllers.Cache.WSCacheManagerImpl;
import com.chatapp.Message.GsonMapper;
import com.chatapp.Services.AuthService;
import com.chatapp.Tokens.WSToken;

import io.javalin.Javalin;


/**
 * Hello world!
 *
 */
enum Roles {
USER,
TOPICOWNER,
ADMIN,
}


public class App 
{
     /*
             * This app provides an rest entpoint for userAuthentification and a webocket endpoint. If user is authenticatet and authorized a one Time sessionId / token is send back to user.
             * The Client than can access the websocket with this sessionId and establish a connection. 
             */

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
            /*This is session container cache with background working thread. Invalid tokens are deleted-> passed time. 
            A limit against token spaming is needed.
            Further design question is, to allow to have a user multiple Websocket sessions. -> multiple clients at the same time etc.
            */
            AChacheManager<String,WSToken> wsSessionIDController = new WSCacheManagerImpl((userToken)-> { 
                var tokenDateTime = LocalDateTime.parse(userToken.validUntil);
                var currentDateTime = LocalDateTime.now();
                return  tokenDateTime.isBefore(currentDateTime);
                },5);

            wsSessionIDController.startController();

            var authService = new AuthService(Algorithm.HMAC256(/*Needs to be put into a variable or otherwise recived*/ "TestSecret"));

            var restController = new RestController(authService,wsSessionIDController);

            var websocketController = new WebsocketController();

            websocketController.setSessionHandler(wsSessionIDController);

            var app = Javalin.create(config -> config.jsonMapper(new GsonMapper()))
            .get("/", ctx -> ctx.result(" Hello World"))
            /* entpoint for user to get a session ID token for beservice path */
            .post("/user",restController::postUser)
            .start(7070);

            /* to establish a connection for the websocket a sessionid received from token is required */
            app.ws("/chat/{id}",websocket -> {
                websocket.onConnect(websocketController::connectUser);
                websocket.onClose(websocketController::disconnectUser);
                websocket.onMessage(websocketController::handleMessage);
            });

            
    }

}

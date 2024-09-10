package com.chatapp;

import java.util.concurrent.ConcurrentHashMap;


import com.auth0.jwt.algorithms.Algorithm;

import com.chatapp.Controllers.RestController;
import com.chatapp.Controllers.WebsocketController;
import com.chatapp.Message.GsonMapper;
import com.chatapp.Message.Protocol;
import com.chatapp.Services.AuthService;
import com.chatapp.Services.ISessionHandler;
import com.chatapp.Tokens.IKeyValuePair;
import com.chatapp.Tokens.UserToken;

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
             * This app provides an rest entpoint for userAuthentification. If user is authenticatet and authorized a oneTime sessionId / token is send back to user.
             * The Client than can acces the websocket with this sessionId and establish a connection. 
             */

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
            /*This needs to be replaced by a session container with background working thread invalid tokens needs to be deleted. 
            Also protection against token spaming is needed.
            Further question is, to allow to have a user multiple Websocket sessions. -> multiple clients etc.
            */
            ConcurrentHashMap<String,UserToken> sessionIds = new ConcurrentHashMap<String,UserToken>();

            var sessionHandler = new ISessionHandler() {

                @Override
                public void addSession(IKeyValuePair<String,UserToken> keyValuePair) {
                    sessionIds.put(keyValuePair.getKey(),keyValuePair.getValue());
                }

                @Override
                public boolean conatinsSession(String sessionId) {
                    return sessionIds.containsKey(sessionId);
                }

                @Override
                public boolean removeSession(String sessionID) {
                    return sessionIds.remove(sessionID) != null;
                }
                
            };

            var authService = new AuthService(Algorithm.HMAC256(/*Needs to be put into a variable or otherwise recived*/ "TestSecret"));

            var restController = new RestController(authService,sessionHandler);

            var websocketController = new WebsocketController();

            websocketController.setSessionHandler(sessionHandler);

            var app = Javalin.create(config -> config.jsonMapper(new GsonMapper()))
            .get("/", ctx -> ctx.result(" Hello World"))
            /* entpoint for user to get a session ID token for beservice path */
            .post("/user",restController::postUser)
            .start(7070);

            /* to establish a connection for the websocket a sessionid received from token is required */
            app.ws("/chat/{id}",websocket -> {
                websocket.onConnect(websocketController::connectUser);
                websocket.onClose(websocketController::disconnectUser);
                websocket.onMessage(ctx -> {
                    Protocol protocol = ctx.messageAsClass(Protocol.class);
                    System.out.println("message : " + protocol.toString());
                });
            });

            
    }

}

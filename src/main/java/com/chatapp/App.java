package com.chatapp;


import org.slf4j.event.KeyValuePair;

import com.auth0.jwt.algorithms.Algorithm;

import com.chatapp.Controllers.RestController;
import com.chatapp.Controllers.WebsocketController;
import com.chatapp.Message.GsonMapper;
import com.chatapp.Message.Protocol;
import com.chatapp.Services.AuthService;

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

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

            var sessionHandler = new ISessionContainer() {

                @Override
                public void addSession(KeyValuePair keyValuePair) {
                    // TODO Auto-generated method stub
                   // throw new UnsupportedOperationException("Unimplemented method 'addSession'");
                }

                @Override
                public boolean conatinsSession(String sessionId) {
                    // TODO Auto-generated method stub
                    //throw new UnsupportedOperationException("Unimplemented method 'conatinsSession'");
                    return false;
                }

                @Override
                public boolean removeSession(String sessionID) {
                    // TODO Auto-generated method stub
                    //throw new UnsupportedOperationException("Unimplemented method 'removeSession'");
                    return true;
                }
                
            };

            var authService = new AuthService(Algorithm.HMAC256("TestSecret"));

            var restController = new RestController(authService,sessionHandler);

            var app = Javalin.create(config -> config.jsonMapper(new GsonMapper()))
            .get("/", ctx -> ctx.result(" Hello World"))
            .post("/user",restController::postUser)
            .start(7070);

            app.ws("/chat/{id}",websocket -> {
                websocket.onConnect(WebsocketController::addUser);
                websocket.onClose(WebsocketController::removeUser);
                websocket.onMessage(ctx -> {
                    Protocol protocol = ctx.messageAsClass(Protocol.class);
                    System.out.println("message : " + protocol.toString());
                    //broadcastMessage(activeUsernameMap.get(ctx), ctx.message());
                });
            });

            
    }

}

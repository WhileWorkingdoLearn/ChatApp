package com.chatapp.Controllers;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.chatapp.User;
import com.chatapp.Message.Protocol;
import com.chatapp.Services.IValidateItem;



public class WebsocketController {

    private  final Map<WsContext,User> activeUsernameMap = new ConcurrentHashMap<>();

    private IValidateItem<String> sessionHandler;

    public void setSessionHandler(IValidateItem<String> handler){
        this.sessionHandler = handler;
    }

    public void connectUser(WsConnectContext ctx){
        /*
         * 1. Validate SessionId
         * 2. Save User Session 
         */
        var value =ctx.pathParam("id");
            if(value.length() > 0 && sessionHandler.isValidWsSessionID(value)){                
                //activeUsernameMap.put(ctx, new User(null, null, null,null,false));
                //sessionHandler.removeSession(value);
                //authentificationUser.removeID(value);
                System.out.println("path :" + value);
            } else {
                ctx.closeSession(org.eclipse.jetty.server.Response.SC_FORBIDDEN, "SESSION_KEY Reqired");
            }
    }

    public void handleMessage(WsMessageContext mctx){
                /*
                1.decryptMessage
                2.validateMessageSender
                3. validateMessageReceiver
                4. send Message to Receiver
                 */
                
            Protocol protocol = mctx.messageAsClass(Protocol.class);
            System.out.println("message : " + protocol.toString());

    }

    public void disconnectUser(WsCloseContext ctx){
        var username = activeUsernameMap.get(ctx);
        activeUsernameMap.remove(ctx);
        System.out.print("Close Socket:" + username);
        //broadcastMessage("Server", (username + " left the chat"));
    }
    

}

package com.chatapp.Controllers;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.chatapp.User;
import com.chatapp.Message.Protocol;
import com.chatapp.Services.IWebSocketSessionIDValidator;



public class WebsocketController {

    private  final Map<WsContext,User> activeUsernameMap = new ConcurrentHashMap<>();

    private IWebSocketSessionIDValidator sessionHandler;

    public void setSessionHandler(IWebSocketSessionIDValidator handler){
        this.sessionHandler = handler;
    }

    public void connectUser(WsConnectContext ctx){
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

package com.chatapp.Controllers;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.chatapp.User;
import com.chatapp.Services.ISessionHandler;



public class WebsocketController {
    private int nextUserNumber = 1;
    private  final Map<WsContext,User> activeUsernameMap = new ConcurrentHashMap<>();

    private ISessionHandler sessionHandler;

    public void setSessionHandler(ISessionHandler handler){
        this.sessionHandler = handler;
    }

    public void connectUser(WsConnectContext ctx){
        var value =ctx.pathParam("id");
            if(value.length() > 0 && sessionHandler.conatinsSession(value)){
                activeUsernameMap.put(ctx, new User(null, null, null,null,false));
                sessionHandler.removeSession(value);
                //authentificationUser.removeID(value);
                System.out.println("path :" + value);
            } else {
                ctx.closeSession(org.eclipse.jetty.server.Response.SC_FORBIDDEN, "SESSION_KEY Reqired");
            }
    }

    public void disconnectUser(WsCloseContext ctx){
        var username = activeUsernameMap.get(ctx);
        activeUsernameMap.remove(ctx);
        System.out.print("Close Socket:" + username);
        //broadcastMessage("Server", (username + " left the chat"));
    }
    

}

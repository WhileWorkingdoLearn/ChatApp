package com.chatapp.Controllers;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.chatapp.User;



public class WebsocketController {
    private static int nextUserNumber = 1;
    private static final Map<WsContext,User> activeUsernameMap = new ConcurrentHashMap<>();
    //private static IAuthentificationService authentificationUser;

    public static void addUser(WsConnectContext ctx){
        var value =ctx.pathParam("id");
            if(value.length() > 0){
                activeUsernameMap.put(ctx, new User(null, null, null,null,false));
                //authentificationUser.removeID(value);
                System.out.println("path :" + value);
            } else {
                ctx.closeSession(org.eclipse.jetty.server.Response.SC_FORBIDDEN, "SESSION_KEY Reqired");
            }
    }

    public static void removeUser(WsCloseContext ctx){
        var username = activeUsernameMap.get(ctx);
        activeUsernameMap.remove(ctx);
        System.out.print("Close Socket:" + username);
        //broadcastMessage("Server", (username + " left the chat"));
    }
    

}

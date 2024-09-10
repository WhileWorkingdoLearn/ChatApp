package com.chatapp.Controllers;

import java.util.Timer;

import com.chatapp.Services.IWebsocketTokenController;
import com.chatapp.Services.IWebSocketSessionIDValidator;
import com.chatapp.Tokens.WbeSocketToken;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;


public class WebSocketSessionIDController implements IWebsocketTokenController, IWebSocketSessionIDValidator {

    private class ClearSessionControllerTask extends java.util.TimerTask {

        @Override
        public void run() {
            if(!sessionContainer.isEmpty()){
                int sizeBefore = sessionContainer.size();
                sessionContainer.entrySet().removeIf((userToken) -> { 
                    var tokenDateTime = LocalDateTime.parse(userToken.getValue().validUntil);
                    var currentDateTime = LocalDateTime.now();
                    return  tokenDateTime.isBefore(currentDateTime);
                });
                int sizeAfter = sessionContainer.size();
                System.out.println("SessionController is sweeping invalid Tokens. Removed : " +  (sizeBefore - sizeAfter));
            }
            System.out.println("SessionController is empty");
        }
        
    }

    private boolean isActive;

    private Timer timer;

    private final ConcurrentHashMap<String,WbeSocketToken> sessionContainer;

    public WebSocketSessionIDController(){
        sessionContainer = new ConcurrentHashMap<>();
        timer = new Timer();
        this.isActive = false;    
    }

    @Override
    public void startController(){
            if(!this.isActive){
                timer.scheduleAtFixedRate(new ClearSessionControllerTask(), 1000,1000);
                this.isActive = true;
            }
    }

    @Override
    public void stopController(){
        if(timer != null){
            timer.cancel();
            this.isActive = false;
        }
    }

    @Override
    public WbeSocketToken generateWebsocketToken(){
        var uniqueSessionId = UUID.randomUUID().toString();
        LocalDateTime utcTimestamp = LocalDateTime.now();
        var tokenForWebsocketConnection = new WbeSocketToken(uniqueSessionId,utcTimestamp.toString(),utcTimestamp.plusSeconds(5).toString());
        sessionContainer.put(tokenForWebsocketConnection.sessionID, tokenForWebsocketConnection);
        return tokenForWebsocketConnection;
    }

    @Override
    public int getCurrentWebSocketTokenCount() {
        return sessionContainer.size();
    }

    @Override
    public void clearAllTokens() {
       this.sessionContainer.clear();        
    }

    @Override
    public boolean isValidWsSessionID(String sessionID) {
        return sessionContainer.containsKey(sessionID);
    }

    @Override
    public void consumeWsToken(String sessionID) {
        
    }

}

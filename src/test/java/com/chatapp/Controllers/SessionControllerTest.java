package com.chatapp.Controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import com.chatapp.Controllers.Cache.ACacheController;
import com.chatapp.Controllers.Cache.WSCacheManagerImpl;
import com.chatapp.Tokens.WSToken;


public class SessionControllerTest {
  
    public ACacheController<String,WSToken> controller= new WSCacheManagerImpl((userToken)-> { 
        var tokenDateTime = LocalDateTime.parse(userToken.validUntil);
        var currentDateTime = LocalDateTime.now();
        return  tokenDateTime.isBefore(currentDateTime);
        },5);

    @Test
    public void testGenerateWebsocketTocken() {
            for (int i = 0; i<10;i++) {
                    controller.createItem();
            }
            assertEquals("Size is not 10",10,controller.getCurrentItemCount());
    }

    @Test
    public void testValidTokensStillinSessionController(){
        controller.clearAllTokens();
        for (int i = 0; i<10;i++) {
            controller.getCurrentItemCount();
         }
        controller.startController();
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        controller.stopController();
        assertEquals("Size is not 10",10, controller.getCurrentItemCount());
    }

    @Test
    public void testTokensDeletedIfInvalid(){
        controller.clearAllTokens();
        for (int i = 0; i<10;i++) {
            controller.createItem();
         }
        controller.startController();
        try {
            Thread.currentThread().sleep(15000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        controller.stopController();
        assertEquals("Size is not 0",0, controller.getCurrentItemCount());

    }
}


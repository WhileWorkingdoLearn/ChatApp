package com.chatapp.Controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SessionControllerTest {
    public SessionController controller= new SessionController();
    @Test
    public void testGenerateWebsocketTocken() {
            for (int i = 0; i<10;i++) {
                    controller.generateWebsocketToken();
            }
            assertEquals("Size is not 10",10,controller.getCurrentWebSocketTokenCount());
    }

    @Test
    public void testValidTokensStillinSessionController(){
        controller.clearAllTokens();
        for (int i = 0; i<10;i++) {
            controller.generateWebsocketToken();
         }
        controller.startController();
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        controller.stopController();
        assertEquals("Size is not 10",10, controller.getCurrentWebSocketTokenCount());
    }

    @Test
    public void testTokensDeletedIfInvalid(){
        controller.clearAllTokens();
        for (int i = 0; i<10;i++) {
            controller.generateWebsocketToken();
         }
        controller.startController();
        try {
            Thread.currentThread().sleep(15000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        controller.stopController();
        assertEquals("Size is not 0",0, controller.getCurrentWebSocketTokenCount());

    }
}


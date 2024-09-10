package com.chatapp.Controllers;

import java.util.Timer;

public class SessionController {

    private class ClearSessionStoreTask extends java.util.TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println("Working");
        }
        
    }

    private boolean isActive;

    private Timer timer;

    public SessionController(){
        timer = new Timer();
        this.isActive = false;    
    }

    public void startController(){
            if(!this.isActive){
                timer.scheduleAtFixedRate(new ClearSessionStoreTask(),  1000,1000);
                this.isActive = true;
            }
    }

    public void stopController(){
        if(timer != null){
            timer.cancel();
            this.isActive = false;
        }
    }

}

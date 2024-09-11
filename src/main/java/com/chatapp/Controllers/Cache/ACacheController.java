package com.chatapp.Controllers.Cache;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


public abstract class ACacheController<Key,Value> implements ICacheController<Value> {

    protected final ConcurrentHashMap<Key,Value> sessionContainer;

    private class ClearControllerTask extends java.util.TimerTask {

        @Override
        public void run() {
            if(!sessionContainer.isEmpty()){
                int sizeBefore = sessionContainer.size();
                sessionContainer.entrySet().removeIf((value)-> clearCallBack.apply(value.getValue()));
                int sizeAfter = sessionContainer.size();
                System.out.println("SessionController is sweeping invalid Tokens. Removed : " +  (sizeBefore - sizeAfter));
            }
            System.out.println("SessionController is empty");
        }
        
    }

    private boolean isActive;

    private Timer timer;

    private final Function<Value,Boolean> clearCallBack;

    private final long interval;

    public ACacheController(Function<Value,Boolean> clearCallBack,long interval){
        this.clearCallBack = clearCallBack == null ? (value) -> true : clearCallBack;
        this.interval = interval;
        sessionContainer = new ConcurrentHashMap<>();
        timer = new Timer();
        this.isActive = false;    
    }

    @Override
    public void startController(){
            if(!this.isActive){
                timer.scheduleAtFixedRate(new ClearControllerTask(), 1000,interval);
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
    public void clearAllTokens() {
       this.sessionContainer.clear();        
    }


    public int getCurrentItemCount(){
        return this.sessionContainer.size();
    }

}


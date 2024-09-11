package com.chatapp.Controllers.Cache;

public interface ICacheController<T> {

    void startController();

    void stopController();

    int getCurrentItemCount();

    void clearAllTokens();

    T createItem();

}
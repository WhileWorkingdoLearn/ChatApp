package com.chatapp.Services;

import com.chatapp.Tokens.WbeSocketToken;

public interface ISessionController {

    void startController();

    void stopController();

    int getCurrentWebSocketTokenCount();

    void clearAllTokens();

    WbeSocketToken generateWebsocketToken();

}
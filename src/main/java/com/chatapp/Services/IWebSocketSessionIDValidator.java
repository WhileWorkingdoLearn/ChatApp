package com.chatapp.Services;

public interface IWebSocketSessionIDValidator {
        public boolean isValidWsSessionID(String sessionID);
        public void consumeWebSocketToken(String sessionID);
}

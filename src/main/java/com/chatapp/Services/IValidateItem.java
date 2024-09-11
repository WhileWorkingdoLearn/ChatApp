package com.chatapp.Services;

public interface IValidateItem<T> {
        public boolean isValidWsSessionID(T sessionID);
        public void consumeWsToken(T sessionID);
}

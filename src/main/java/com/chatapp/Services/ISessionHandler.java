package com.chatapp.Services;

import com.chatapp.Tokens.IKeyValuePair;
import com.chatapp.Tokens.UserToken;

public interface ISessionHandler {

   public void addSession(IKeyValuePair<String,UserToken> keyValuePair);

   public boolean conatinsSession(String sessionId);

   public boolean removeSession(String sessionID);

}

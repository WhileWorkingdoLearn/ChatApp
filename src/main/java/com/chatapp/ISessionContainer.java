package com.chatapp;

import org.slf4j.event.KeyValuePair;

public interface ISessionContainer {

   public void addSession(KeyValuePair keyValuePair);

   public boolean conatinsSession(String sessionId);

   public boolean removeSession(String sessionID);

}

package com.chatapp.Controllers.Cache;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import com.chatapp.Tokens.WSToken;
import java.time.LocalDateTime;

public class WSCacheManagerImpl extends  AChacheManager<String,WSToken> {
    public WSCacheManagerImpl(Function<WSToken, Boolean> clearCallBack, long interval) {
        super(clearCallBack, interval,new ConcurrentHashMap<String,WSToken>());
    }

    @Override
    public WSToken createItem() {
        var uniqueSessionId = UUID.randomUUID().toString();
        LocalDateTime utcTimestamp = LocalDateTime.now();
        var tokenForWebsocketConnection = new WSToken(uniqueSessionId,utcTimestamp.toString(),utcTimestamp.plusSeconds(5).toString());
        this.sessionContainer.put(tokenForWebsocketConnection.sessionID, tokenForWebsocketConnection);
        return tokenForWebsocketConnection;
    }

    @Override
    public boolean isValidWsSessionID(String sessionID) {
        return true;
    }

    @Override
    public void consumeWsToken(String sessionID) {
        
    }

    
}

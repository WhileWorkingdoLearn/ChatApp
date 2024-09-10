package com.chatapp.Tokens;

public class WbeSocketToken {
    public final String sessionID;
    public final String issued;
    public final String validUntil;

    public WbeSocketToken(String sessionID, String issued, String validUntil) {
        this.sessionID = sessionID;
        this.issued = issued;
        this.validUntil = validUntil;
    }


    
}

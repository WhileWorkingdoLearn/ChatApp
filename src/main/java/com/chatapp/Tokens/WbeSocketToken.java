package com.chatapp.Tokens;

public class WbeSocketToken  {
    public final String sessionID;
    public final String issuedDateTime;
    public final String validUntil;

    public WbeSocketToken(String sessionID, String issueDateTime, String validUntil) {
        this.sessionID = sessionID;
        this.issuedDateTime = issueDateTime;
        this.validUntil = validUntil;
    }
    
}

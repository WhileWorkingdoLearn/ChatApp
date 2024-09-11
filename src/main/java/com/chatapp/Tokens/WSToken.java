package com.chatapp.Tokens;

public class WSToken  {
    public final String sessionID;
    public final String issuedDateTime;
    public final String validUntil;

    public WSToken(String sessionID, String issueDateTime, String validUntil) {
        this.sessionID = sessionID;
        this.issuedDateTime = issueDateTime;
        this.validUntil = validUntil;
    }
    
}

package com.chatapp.Message;

import com.chatapp.Tokens.UserToken;

/*
 {
    "version":"",
    "fromID":"",
    "token":{}
    "groupId": "",
    "UserId":"",
    "messageTyoe":"",
    "encoding":"",
    "encryption":"",
    "length":"",
    "data":""
 }
 */
public class Protocol{
    public  String version;
    public  String fromID;
    public  UserToken token;
    public  String groupId;
    public  String UssrID;
    public  String messageType;
    public  String encoding;
    public  String encryption;
    public  int length;
    public  String data;

    public Protocol(String version, String fromID, UserToken token, String groupId, String ussrID, String messageType,
            String encoding, String encryption, int length,String data) {
        this.version = version;
        this.fromID = fromID;
        this.token = token;
        this.groupId = groupId;
        UssrID = ussrID;
        this.messageType = messageType;
        this.encoding = encoding;
        this.encryption = encryption;
        this.length = length;
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public UserToken getToken() {
        return token;
    }

    public void setToken(UserToken token) {
        this.token = token;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUssrID() {
        return UssrID;
    }

    public void setUssrID(String ussrID) {
        UssrID = ussrID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Protocol [version=" + version + ", fromID=" + fromID + ", token=" + token + ", groupId=" + groupId
                + ", UssrID=" + UssrID + ", messageType=" + messageType + ", encoding=" + encoding + ", encryption="
                + encryption + ", length=" + length + ", data=" + data + "]";
    }
    
        
}
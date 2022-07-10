package com.cs5520.cs5520_a8;

public class StickerExchangeDetails {
    public String senderId;
    public String stickerId;
    public String dateSent;
    public String receiverId;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getStickerId() {
        return stickerId;
    }

    public String getDateSent() {
        return dateSent;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public StickerExchangeDetails(String senderId, String stickerId, String dateSent, String receiverId) {
        this.senderId = senderId;
        this.stickerId = stickerId;
        this.dateSent = dateSent;
        this.receiverId = receiverId;
    }
}



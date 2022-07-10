package com.cs5520.cs5520_a8;

public class ReceivedHistoryCollector {
    public String senderId;
    public String dateSent;
    public int sticker;

    public ReceivedHistoryCollector(String senderId, String dateSent, int sticker) {
        this.senderId = senderId;
        this.dateSent = dateSent;
        this.sticker = sticker;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public String getDateSent() {
        return this.dateSent;
    }

    public int getSticker() {
        return this.sticker;
    }
}

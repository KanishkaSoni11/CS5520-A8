package com.cs5520.cs5520_a8;

import android.view.View;

public class ReceivedHistoryCollector {
    public String senderId;
    public String dateSent;
    public String sticker;

    public ReceivedHistoryCollector(String senderId, String dateSent, String sticker) {
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

    public String getSticker() {
        return this.sticker;
    }
}

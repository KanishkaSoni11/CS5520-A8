package com.cs5520.cs5520_a8;

public class SentHistory {

    private String sticker;
    private String count;

    public SentHistory(String sticker, String count) {
        this.sticker = sticker;
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public String getSticker() {
        return sticker;
    }
}

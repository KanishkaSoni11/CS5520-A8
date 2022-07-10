package com.cs5520.cs5520_a8;

public class SentHistory {

    private String sticker;
    private int count;

    public SentHistory(String sticker, int count) {
        this.sticker = sticker;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getSticker() {
        return sticker;
    }
}

package com.cs5520.cs5520_a8;

import java.util.List;

public class Sticker {
    private List<String> stickerList;

    public Sticker() {

    }

    public List<String> getStickerList() {
        return stickerList;
    }

    public void setStickerList(List<String> stickerList) {
        this.stickerList = stickerList;
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "stickerList=" + stickerList +
                '}';
    }
}

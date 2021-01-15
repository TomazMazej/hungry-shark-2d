package com.feri.hungryshark2d.retrofit;

public class PostRequest {
    private String id;
    private int coins;
    private String skin;

    public PostRequest(String id, int coins, String skin) {
        this.id = id;
        this.coins = coins;
        this.skin = skin;
    }

    public String getId() {
        return id;
    }

    public int getCoins() {
        return coins;
    }

    public String getSkin() {
        return skin;
    }
}

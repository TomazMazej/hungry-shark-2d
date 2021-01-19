package com.feri.hungryshark2d.retrofit;
import java.util.ArrayList;

public class GetSkinsRequest {
    private ArrayList<String> skins;

    public GetSkinsRequest(ArrayList<String> skins) {
        this.skins = skins;
    }

    public ArrayList<String>  getSkins() {
        return skins;
    }
}

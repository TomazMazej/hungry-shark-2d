package com.feri.hungryshark2d.desktop;


import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main (String[] arg) {
       TexturePacker.Settings settings = new TexturePacker.Settings();

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "gameplay"
        );

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/menu",
                ASSETS_PATH + "/menu",
                "menu"
        );

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/shop",
                ASSETS_PATH + "/shop",
                "shop"
        );
    }
}

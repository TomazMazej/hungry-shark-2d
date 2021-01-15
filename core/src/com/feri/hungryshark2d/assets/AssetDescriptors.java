package com.feri.hungryshark2d.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feri.hungryshark2d.screens.Gameplay;

public class AssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Music> BACKGROUND_MUSIC =
            new AssetDescriptor<Music>(AssetPaths.BACKGROUND_MUSIC, Music.class);

    public static final AssetDescriptor<Sound> EAT_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.EAT_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> MINE_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.MINE_SOUND, Sound.class);

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<TextureAtlas> MENU =
            new AssetDescriptor<TextureAtlas>(AssetPaths.MENU, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SHOP =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SHOP, TextureAtlas.class);

    public AssetDescriptors() {
    }
}

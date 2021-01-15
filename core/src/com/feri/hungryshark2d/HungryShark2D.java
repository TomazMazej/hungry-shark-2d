package com.feri.hungryshark2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;
import com.feri.hungryshark2d.assets.AssetDescriptors;
import com.feri.hungryshark2d.assets.AssetPaths;
import com.feri.hungryshark2d.screens.GameOver;
import com.feri.hungryshark2d.screens.Gameplay;
import com.feri.hungryshark2d.screens.MainMenu;
import com.feri.hungryshark2d.screens.Shop;
import com.feri.hungryshark2d.util.GameManager;

public class HungryShark2D extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Logger.DEBUG);

		assetManager = new AssetManager();
		assetManager.getLogger().setLevel(Logger.DEBUG);
		batch = new SpriteBatch();

		loadAssets();
		setScreen(new MainMenu(this));
	}

	public void loadAssets(){
		assetManager.load(AssetDescriptors.GAME_PLAY);
		assetManager.load(AssetDescriptors.BACKGROUND_MUSIC);
		assetManager.load(AssetDescriptors.EAT_SOUND);
		assetManager.load(AssetDescriptors.MINE_SOUND);
		assetManager.load(AssetDescriptors.UI_SKIN);
		assetManager.load(AssetDescriptors.MENU);
		assetManager.load(AssetDescriptors.SHOP);
		assetManager.load(AssetDescriptors.FONT);
		assetManager.finishLoading();
	}

	public static enum Screens {
		MAIN, GAMEPLAY, GAMEOVER, SHOP
	}

	public void gotoScreen(Screens screenType) {

		switch (screenType) {
			case MAIN:
				super.setScreen(new MainMenu(this));
				break;
			case GAMEPLAY:
				super.setScreen(new Gameplay(this));
				GameManager.INSTANCE.setScore(0);
				break;
			case GAMEOVER:
				super.setScreen(new GameOver(this));
				break;
			case SHOP:
				super.setScreen(new Shop(this));
				break;
		}
		System.gc();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		batch.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}

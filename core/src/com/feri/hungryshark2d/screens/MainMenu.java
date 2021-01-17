package com.feri.hungryshark2d.screens;

import static com.feri.hungryshark2d.config.GameConfig.HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.WIDTH;
import static com.feri.hungryshark2d.util.GameManager.INSTANCE;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.feri.hungryshark2d.HungryShark2D;
import com.feri.hungryshark2d.assets.AssetDescriptors;
import com.feri.hungryshark2d.assets.RegionNames;
import com.feri.hungryshark2d.util.GameManager;

public class MainMenu extends ScreenAdapter {
    private HungryShark2D game;
    private AssetManager manager;
    private FillViewport fillViewport;
    private Stage stage;
    private Music background_music;

    public MainMenu(HungryShark2D hs2d)  {
        this.game = hs2d;
        manager = game.getAssetManager();

        GameManager.INSTANCE.getDataFromBC();

        background_music = manager.get(AssetDescriptors.BACKGROUND_MUSIC);
        background_music.play();
        background_music.isLooping();
        if (INSTANCE.getMute()) {
            background_music.pause();
        }
    }

    @Override
    public void show() {
        fillViewport = new FillViewport(WIDTH, HEIGHT);
        stage = new Stage(fillViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);
        stage.addActor(createUI());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    private Actor createUI(){
        Table table = new Table();
        table.defaults().pad(10);

        TextureAtlas gamePlayAtlas = manager.get(AssetDescriptors.MENU);

        TextureRegion bg = gamePlayAtlas.findRegion(RegionNames.BG_MENU);
        table.setBackground(new TextureRegionDrawable(bg));

        TextureRegion tittleImage = gamePlayAtlas.findRegion(RegionNames.TITLE);
        Image title = new Image(tittleImage);
        //.setColor(Color.BLUE);

        TextureRegion playButton = gamePlayAtlas.findRegion(RegionNames.PLAY_B);
        TextureRegion shopButton = gamePlayAtlas.findRegion(RegionNames.SHOP_B);
        final TextureRegion muteButton = gamePlayAtlas.findRegion(RegionNames.MUTE_B);
        final TextureRegion unmuteButton = gamePlayAtlas.findRegion(RegionNames.UNMUTE_B);
        TextureRegion exitButton = gamePlayAtlas.findRegion(RegionNames.EXIT_B);

        ImageButton.ImageButtonStyle playBImageStyle = new ImageButton.ImageButtonStyle();
        playBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(playButton));
        ImageButton playBImageButton = new ImageButton(playBImageStyle);
        playBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(HungryShark2D.Screens.GAMEPLAY);
            }
        });

        ImageButton.ImageButtonStyle shopBImageStyle = new ImageButton.ImageButtonStyle();
        shopBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shopButton));
        ImageButton shopBImageButton = new ImageButton(shopBImageStyle);
        shopBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(HungryShark2D.Screens.SHOP);
            }
        });

        ImageButton.ImageButtonStyle exitBImageStyle = new ImageButton.ImageButtonStyle();
        exitBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(exitButton));
        ImageButton exitBImageButton = new ImageButton(exitBImageStyle);
        exitBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        final TextureRegion selected;
        if(INSTANCE.getMute()) {
            selected = muteButton;
        }
        else {
            selected = unmuteButton;
        }
        ImageButton.ImageButtonStyle muteBImageStyle = new ImageButton.ImageButtonStyle();
        muteBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(selected));
        ImageButton muteBImageButton = new ImageButton(muteBImageStyle);
        muteBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                INSTANCE.changeMute();
                if (INSTANCE.getMute()) {
                    background_music.pause();
                }
                else {
                    background_music.play();
                }
                show();
            }
        });

        table.add(muteBImageButton).padRight(-500);
        table.row();
        table.add(title).padTop(-40);
        table.row();
        table.add(playBImageButton).padTop(50).row();
        table.add(shopBImageButton).row();
        table.add(exitBImageButton).row();

        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    @Override
    public void hide() {
        super.hide();
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        fillViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}


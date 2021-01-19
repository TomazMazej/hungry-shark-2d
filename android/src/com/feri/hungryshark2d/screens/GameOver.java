package com.feri.hungryshark2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.feri.hungryshark2d.HungryShark2D;
import com.feri.hungryshark2d.assets.AssetDescriptors;
import com.feri.hungryshark2d.assets.RegionNames;
import com.feri.hungryshark2d.util.GameManager;

import static com.feri.hungryshark2d.config.GameConfig.HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.WIDTH;


public class GameOver extends ScreenAdapter {

    private FillViewport fillViewport;
    public static BitmapFont font;
    private HungryShark2D game;
    private AssetManager manager;
    private Stage stage;

    public GameOver(HungryShark2D hs2d)  {
        this.game = hs2d;
        manager = game.getAssetManager();
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

        //font = manager.get(AssetDescriptors.FONT2);
        font = GameManager.generateFont2();

        TextureAtlas gamePlayAtlas = manager.get(AssetDescriptors.MENU);

        TextureRegion bg = gamePlayAtlas.findRegion(RegionNames.BG_MENU);
        table.setBackground(new TextureRegionDrawable(bg));

        TextureRegion replayButton = gamePlayAtlas.findRegion(RegionNames.RESTART_B);
        TextureRegion homeButton = gamePlayAtlas.findRegion(RegionNames.HOME_B);

        TextureRegion gameOverImage = gamePlayAtlas.findRegion(RegionNames.GAMEOVER);
        Image theEnd = new Image(gameOverImage);

        Label score;
        score = new Label("SCORE: " + GameManager.INSTANCE.getScore(), new Label.LabelStyle(font, Color.valueOf("#AED6F1")));

        Label highScore;
        highScore = new Label("HIGHSCORE: " + GameManager.INSTANCE.getHighScore(), new Label.LabelStyle(font, Color.valueOf("#AED6F1")));

        ImageButton.ImageButtonStyle playBImageStyle = new ImageButton.ImageButtonStyle();
        playBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(replayButton));
        ImageButton playBImageButton = new ImageButton(playBImageStyle);
        playBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(HungryShark2D.Screens.GAMEPLAY);
            }
        });

        ImageButton.ImageButtonStyle homeBImageStyle = new ImageButton.ImageButtonStyle();
        homeBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(homeButton));
        ImageButton homeBImageButton = new ImageButton(homeBImageStyle);
        homeBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(HungryShark2D.Screens.MAIN);
            }
        });

        table.add(theEnd).padTop(-20);
        table.row();
        table.add(score);
        table.row();
        table.add(highScore);
        table.row();
        table.add(playBImageButton).padTop(40).row();
        table.add(homeBImageButton).row();
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


package com.feri.hungryshark2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.feri.hungryshark2d.HungryShark2D;
import com.feri.hungryshark2d.assets.AssetDescriptors;
import com.feri.hungryshark2d.assets.RegionNames;

import java.util.ArrayList;

import static com.feri.hungryshark2d.config.GameConfig.HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.WIDTH;
import static com.feri.hungryshark2d.util.GameManager.INSTANCE;

public class Shop extends ScreenAdapter {
    private HungryShark2D game;
    private AssetManager manager;
    private FillViewport fillViewport;
    private Stage stage;
    private Skin skin;
    private Label coins;
    private int[] prices = {0,150,200,250,300};

    public Shop(HungryShark2D hs2d)  {
        this.game = hs2d;
        manager = game.getAssetManager();
    }

    @Override
    public void show() {
        fillViewport = new FillViewport(WIDTH, HEIGHT);
        stage = new Stage(fillViewport, game.getBatch());
        skin = manager.get(AssetDescriptors.UI_SKIN);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(createUI());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    private boolean selectSkin(int id){
        if(INSTANCE.hasSkin(id)){
            INSTANCE.setSharkId(id);
            game.gotoScreen(HungryShark2D.Screens.GAMEPLAY);
        }else if(INSTANCE.getCoins() >= prices[id]){
            coins.setText(INSTANCE.setSkin(prices[id],""+id));
            return true;
        }
        return false;
    }

    private String ownsSkin(int id){
        if(INSTANCE.hasSkin(id)){
            return "";
        }
        return "" + prices[id];
    }

    private Actor createUI(){
        final Table table = new Table();
        table.defaults().pad(10);

        TextureAtlas gamePlayAtlas = manager.get(AssetDescriptors.SHOP);

        TextureRegion bg = gamePlayAtlas.findRegion(RegionNames.BG_SHOP);
        table.setBackground(new TextureRegionDrawable(bg));

        TextureAtlas gameplayAtlas = manager.get(AssetDescriptors.MENU);
        TextureRegion tittleImage = gameplayAtlas.findRegion(RegionNames.SHOP_B);
        Image title = new Image(tittleImage);

        TextureRegion shark1 = gamePlayAtlas.findRegion(RegionNames.SHARK1);
        TextureRegion shark2 = gamePlayAtlas.findRegion(RegionNames.SHARK2);
        TextureRegion shark3 = gamePlayAtlas.findRegion(RegionNames.SHARK3);
        TextureRegion shark4 = gamePlayAtlas.findRegion(RegionNames.SHARK4);
        TextureRegion shark5 = gamePlayAtlas.findRegion(RegionNames.SHARK5);
        TextureRegion shark6 = gamePlayAtlas.findRegion(RegionNames.SHARK6);
        TextureRegion backButton = gamePlayAtlas.findRegion(RegionNames.BACK_B);
        TextureRegion scanButton = gamePlayAtlas.findRegion(RegionNames.SCAN_B);
        TextureRegion coin = gamePlayAtlas.findRegion(RegionNames.COIN);
        TextureRegion coin2 = gamePlayAtlas.findRegion(RegionNames.COIN2);

        Image coin2Image = new Image(new TextureRegionDrawable(new TextureRegion(coin2)));
        coin2Image.setSize(20,20);
        Image coin3Image = new Image(new TextureRegionDrawable(new TextureRegion(coin2)));
        coin2Image.setSize(20,20);
        Image coin4Image = new Image(new TextureRegionDrawable(new TextureRegion(coin2)));
        coin2Image.setSize(20,20);
        Image coin5Image = new Image(new TextureRegionDrawable(new TextureRegion(coin2)));
        coin2Image.setSize(20,20);

        Image coinImage = new Image(new TextureRegionDrawable(new TextureRegion(coin)));
        coinImage.setSize(5,5);
        coins = new Label(Integer.toString(INSTANCE.getCoins()), skin);

        final Label price1 = new Label("",skin);

        ImageButton.ImageButtonStyle shark1ImageStyle = new ImageButton.ImageButtonStyle();
        shark1ImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shark1));
        ImageButton shark1ImageButton = new ImageButton(shark1ImageStyle);
        shark1ImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                INSTANCE.setSharkId(0);
                game.gotoScreen(HungryShark2D.Screens.GAMEPLAY);
            }
        });

        final Label price2 = new Label(ownsSkin(1),skin);

        final Group grp = new Group();
        if(!price2.textEquals("")) {
            coin2Image.setPosition(-25, 3);
            grp.addActor(coin2Image);
            grp.addActorAfter(coin2Image, price2);
        }

        ImageButton.ImageButtonStyle shark2ImageStyle = new ImageButton.ImageButtonStyle();
        shark2ImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shark2));
        ImageButton shark2ImageButton = new ImageButton(shark2ImageStyle);
        shark2ImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(selectSkin(1)){
                    price2.setText("");
                    grp.clear();
                }
            }
        });

        final Label price3 = new Label(ownsSkin(2),skin);
        final Group grp2 = new Group();
        if(!price3.textEquals("")) {
            coin3Image.setPosition(-25, 3);
            grp2.addActor(coin3Image);
            grp2.addActorAfter(coin3Image, price3);
        }

        ImageButton.ImageButtonStyle shark3ImageStyle = new ImageButton.ImageButtonStyle();
        shark3ImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shark3));
        ImageButton shark3ImageButton = new ImageButton(shark3ImageStyle);
        shark3ImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(selectSkin(2)){
                    price3.setText("");
                    grp2.clear();
                }
            }
        });

        final Label price4 = new Label(ownsSkin(3),skin);
        final Group grp3 = new Group();
        if(!price4.textEquals("")) {
            coin4Image.setPosition(-25, 2);
            grp3.addActor(coin4Image);
            grp3.addActorAfter(coin4Image, price4);
        }

        ImageButton.ImageButtonStyle shark4ImageStyle = new ImageButton.ImageButtonStyle();
        shark4ImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shark4));
        ImageButton shark4ImageButton = new ImageButton(shark4ImageStyle);
        shark4ImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(selectSkin(3)){
                    price4.setText("");
                    grp3.clear();
                }
            }
        });

        final Label price5 = new Label(ownsSkin(4),skin);
        final Group grp4 = new Group();
        if(!price5.textEquals("")) {
            coin5Image.setPosition(-25, 2);
            grp4.addActor(coin5Image);
            grp4.addActorAfter(coin5Image, price5);
        }

        ImageButton.ImageButtonStyle shark5ImageStyle = new ImageButton.ImageButtonStyle();
        shark5ImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shark5));
        ImageButton shark5ImageButton = new ImageButton(shark5ImageStyle);
        shark5ImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(selectSkin(4)){
                    price5.setText("");
                    grp4.clear();
                }
            }
        });

        ImageButton.ImageButtonStyle shark6ImageStyle = new ImageButton.ImageButtonStyle();
        shark6ImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shark6));
        ImageButton shark6ImageButton = new ImageButton(shark6ImageStyle);
        shark6ImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        ImageButton.ImageButtonStyle backBImageStyle = new ImageButton.ImageButtonStyle();
        backBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backButton));
        ImageButton backBImageButton = new ImageButton(backBImageStyle);
        backBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gotoScreen(HungryShark2D.Screens.MAIN);
            }
        });

        ImageButton.ImageButtonStyle scanBImageStyle = new ImageButton.ImageButtonStyle();
        scanBImageStyle.imageUp = new TextureRegionDrawable(new TextureRegion(scanButton));
        ImageButton scanBImageButton = new ImageButton(scanBImageStyle);
        scanBImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });

        table.add(coins).padRight(-780).padTop(10);
        table.add(coinImage).padRight(-450).padTop(10);
        table.add(title).padLeft(-250).padTop(15);
        table.row();
        table.columnDefaults(1);
        table.add(shark1ImageButton).padTop(10);
        table.add(shark2ImageButton).padTop(10);
        table.add(shark3ImageButton).padTop(10).row();
        table.add(price1);
        table.add(grp);
        table.add(grp2).row();
        table.add(shark4ImageButton).padTop(-10);
        table.add(shark5ImageButton).padTop(-10);
        table.add(shark6ImageButton).padTop(-10).row();
        table.add(grp3).padTop(30);
        table.add(grp4).padTop(30).row();
        table.add(backBImageButton).padLeft(-230).padTop(-10);
        table.add(scanBImageButton).padRight(-470).padTop(-10);

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


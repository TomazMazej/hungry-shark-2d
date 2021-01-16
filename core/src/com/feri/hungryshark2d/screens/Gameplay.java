package com.feri.hungryshark2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.feri.hungryshark2d.HungryShark2D;
import com.feri.hungryshark2d.assets.AssetDescriptors;
import com.feri.hungryshark2d.assets.RegionNames;
import com.feri.hungryshark2d.debug.DebugCameraController;
import com.feri.hungryshark2d.debug.DebugViewportUtils;
import com.feri.hungryshark2d.debug.MemoryInfo;
import com.feri.hungryshark2d.objects.Fish;
import com.feri.hungryshark2d.objects.GameObjectDynamic;
import com.feri.hungryshark2d.objects.Mine;
import com.feri.hungryshark2d.objects.Shark;
import com.feri.hungryshark2d.util.GameManager;

import java.util.Iterator;

import static com.feri.hungryshark2d.util.GameManager.INSTANCE;
import static com.feri.hungryshark2d.util.GameManager.generateFont;
import static com.feri.hungryshark2d.util.GameManager.getFontSize;
import static com.feri.hungryshark2d.config.GameConfig.FISH_HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.FISH_SPAWN_TIME;
import static com.feri.hungryshark2d.config.GameConfig.FISH_SPEED;
import static com.feri.hungryshark2d.config.GameConfig.FISH_WIDTH;
import static com.feri.hungryshark2d.config.GameConfig.HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.MINE_HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.MINE_SPAWN_TIME;
import static com.feri.hungryshark2d.config.GameConfig.MINE_SPEED;
import static com.feri.hungryshark2d.config.GameConfig.MINE_WIDTH;
import static com.feri.hungryshark2d.config.GameConfig.SHARK_HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.SHARK_WIDTH;
import static com.feri.hungryshark2d.config.GameConfig.WIDTH;

public class Gameplay extends ScreenAdapter {
    private HungryShark2D game;
    private OrthographicCamera camera;
    private ScreenViewport viewportFont;
    private SpriteBatch batch;

    private Shark shark;
    private Array<GameObjectDynamic> gameObjects; //special LibGDX Array
    public int score;
    private AssetManager assetManager;
    public static BitmapFont font;

    //debug
    private MemoryInfo memoryInfo;
    private DebugCameraController dcc;
    private ShapeRenderer sr;
    public Viewport vp;
    private boolean debug = false;

    //particles
    private ParticleEffect particle1;
    private ParticleEffect particle2;

    //texture regions
    TextureAtlas gamePlayAtlas;
    private static TextureRegion fishImage;
    private static TextureRegion mineImage;
    private static TextureRegion sharkImage;
    private static TextureRegion backgroundImage;
    private static TextureRegion blankImage;
    private Sound fish_sound;
    private Sound mine_sound;
    private Music bg_music;

    public Gameplay(HungryShark2D hs2d) {
        batch = new SpriteBatch();
        this.game = hs2d;
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        viewportFont = new ScreenViewport(camera);
        viewportFont.update((int)WIDTH, (int)HEIGHT, true);
        camera.update();

        gameObjects = new Array<GameObjectDynamic>();
        assetManager = hs2d.getAssetManager();
        shark = new Shark();

        //debug
        memoryInfo = new MemoryInfo(500);
        dcc = new DebugCameraController();
        dcc.setStartPosition((float) WIDTH / 2, (float) HEIGHT / 2);
        vp = new FitViewport(WIDTH, HEIGHT, camera);
        sr = new ShapeRenderer();

        //particle
        particle1 = new ParticleEffect();
        particle1.load(Gdx.files.internal("particle/vulkan.p"),Gdx.files.internal("particle"));
        particle1.getEmitters().first().setPosition(Gdx.graphics.getWidth()/4f + 16,10);
        particle1.getEmitters().first();
        particle1.start();

        particle2 = new ParticleEffect();
        particle2.load(Gdx.files.internal("particle/vulkan.p"),Gdx.files.internal("particle"));
        particle2.getEmitters().first().setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/4f - 16,10);
        particle2.getEmitters().first();
        particle2.start();

        init();
        spawnFish();
        spawnMines();
    }

    private void spawnFish() {
        Fish.setLastFishTime(TimeUtils.nanoTime());
        gameObjects.add(Fish.poolFish.obtain());
    }

    private void spawnMines() {
        Mine.setLastMineTime(TimeUtils.nanoTime());
        gameObjects.add(Mine.poolMines.obtain());
    }

    public void init(){
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        fishImage = gamePlayAtlas.findRegion(RegionNames.FISH);
        mineImage = gamePlayAtlas.findRegion(RegionNames.MINE);
        sharkImage = setLeftShark(INSTANCE.getSharkId());
        backgroundImage = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        blankImage = gamePlayAtlas.findRegion(RegionNames.BLANK);
        mine_sound = assetManager.get(AssetDescriptors.MINE_SOUND);
        fish_sound = assetManager.get(AssetDescriptors.EAT_SOUND);
        bg_music = assetManager.get(AssetDescriptors.BACKGROUND_MUSIC);
        font = generateFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 5f, 1); //clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined); // tell the SpriteBatch to render in the coordinate system specified by the camera.

        batch.begin();
        {
            //draw stuff
            batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(sharkImage, shark.position.x, shark.position.y, SHARK_WIDTH, SHARK_HEIGHT);
            particle1.update(Gdx.graphics.getDeltaTime());
            particle1.draw(batch);
            particle2.update(Gdx.graphics.getDeltaTime());
            particle2.draw(batch);

            for (GameObjectDynamic object : gameObjects) {
                if(object instanceof Fish){
                    batch.draw(fishImage, object.position.x, object.position.y, FISH_WIDTH, FISH_HEIGHT);
                }
                if(object instanceof Mine){
                    batch.draw(mineImage, object.position.x, object.position.y, MINE_WIDTH, MINE_HEIGHT);
                }
            }

            float w = getFontSize(font, "" + INSTANCE.getScore());

            font.setColor(Color.WHITE); //draw score and health
            font.draw(batch, "" + INSTANCE.getScore(), Gdx.graphics.getWidth()/2f-w/2, Gdx.graphics.getHeight() - 20);

            //health bar
            if(Shark.sharkHealth > 0.6f)
                batch.setColor(Color.GREEN);
            else if(Shark.sharkHealth > 0.2f)
                batch.setColor(Color.ORANGE);
            else
                batch.setColor(Color.RED);
            batch.draw(blankImage, 0, 0, Gdx.graphics.getWidth() * Shark.sharkHealth, 5);
            batch.setColor(Color.WHITE);
        }
        batch.end();

        if (particle1.isComplete())
            particle1.reset();
        if (particle2.isComplete())
            particle2.reset();

        //debug
        if (debug) {
            memoryInfo.update();
            dcc.handleDebugInput(Gdx.graphics.getDeltaTime());
            dcc.applyTo(camera);
            batch.begin();
            {
                GlyphLayout layout = new GlyphLayout(font, "FPS:"+Gdx.graphics.getFramesPerSecond());
                font.setColor(Color.YELLOW);
                font.draw(batch,layout,Gdx.graphics.getWidth()-layout.width-50, Gdx.graphics.getHeight()-20);

                font.setColor(Color.YELLOW);
                font.draw(batch, "RC:" + batch.totalRenderCalls, 50, Gdx.graphics.getHeight() - 20);
                memoryInfo.render(batch,font);
            }
            batch.end();

            batch.totalRenderCalls = 0;

            DebugViewportUtils.drawGrid(vp, sr, 50);

            //Print rectangles
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeRenderer.ShapeType.Line);
            {
                sr.setColor(1, 1, 0, 1);
                for (GameObjectDynamic object : gameObjects) {
                    if (object instanceof Fish) {
                        sr.rect(object.getPosition().x, object.getPosition().y, FISH_WIDTH, FISH_HEIGHT);
                    }
                    else{
                        sr.rect(object.getPosition().x, object.getPosition().y, MINE_WIDTH, MINE_HEIGHT);
                    }
                }
                sr.rect(shark.getPosition().x, shark.getPosition().y, SHARK_WIDTH, SHARK_HEIGHT);
            }
            sr.end();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F5)) debug=!debug;
        if (Shark.sharkHealth > 0) { //is game end?
            if(TimeUtils.nanoTime() - Fish.getLastFishTime() > FISH_SPAWN_TIME) spawnFish(); // check if we need to create a new fish or mine
            if(TimeUtils.nanoTime() - Mine.getLastMineTime() > MINE_SPAWN_TIME) spawnMines();

            shark.update(Gdx.graphics.getDeltaTime());
            for(GameObjectDynamic o: gameObjects){
                o.update(Gdx.graphics.getDeltaTime());
            }
            if (Gdx.input.isTouched()) { // process user input
                if ((Gdx.input.getX() < Gdx.graphics.getWidth() / 2)){ //left
                    sharkImage = setLeftShark(INSTANCE.getSharkId());
                    shark.commandMoveLeft();
                } else { //right
                    sharkImage = setRightShark(INSTANCE.getSharkId());
                    shark.commandMoveRight();
                }
            }
            //desktop controlls
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                sharkImage = setLeftShark(INSTANCE.getSharkId());
                shark.commandMoveLeft();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                sharkImage = setRightShark(INSTANCE.getSharkId());
                shark.commandMoveRight();
            }

            //handle colission with mines and fish
            for (Iterator<GameObjectDynamic> iter = gameObjects.iterator(); iter.hasNext(); ) {
                GameObjectDynamic object = iter.next();
                if(object instanceof Mine){
                    object.position.y -= MINE_SPEED * Gdx.graphics.getDeltaTime();
                    if (object.position.y + MINE_HEIGHT < 0){
                        iter.remove();
                        ((Mine) object).finish();
                    }
                    if (object.bounds.overlaps(shark.bounds)) {
                        if (!INSTANCE.getMute())
                            mine_sound.play();
                        //TODO; change to 0.1
                        Shark.sharkHealth -= 1;
                        ((Mine) object).finish();
                        iter.remove();
                    }
                }
                else{
                    object.position.y -= FISH_SPEED * Gdx.graphics.getDeltaTime();
                    if (object.position.y + FISH_HEIGHT < 0){
                        iter.remove(); //From screen
                        ((Fish) object).finish();
                    }
                    if (object.bounds.overlaps(shark.bounds)) {
                        if (!INSTANCE.getMute()) {
                            fish_sound.play();
                        }
                        //TODO: change to 10
                        score = INSTANCE.incrementScore(10000);
                        if (score%100==0)
                            MINE_SPEED += 100; //speeds up
                        ((Fish) object).finish();
                        iter.remove(); //smart Array enables remove from Array
                    }
                }
            }
        } else { //health of shark is 0 or less
            batch.begin();
            {
                sharkImage = setDeadShark(INSTANCE.getSharkId());
                font.setColor(Color.RED);
                float w = getFontSize(font,"GAMEOVER");
                //font.draw(batch, "GAMEOVER", Gdx.graphics.getWidth()/2f - w/2, Gdx.graphics.getHeight() / 2f);
                INSTANCE.setCoins(score/10);
                game.gotoScreen(HungryShark2D.Screens.GAMEOVER);
            }
            batch.end();
        }
    }

    public TextureAtlas.AtlasRegion setLeftShark(int id) {
        if (id == 0) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK_LEFT);
        }
        else if (id == 1) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK2_LEFT);
        }
        else if (id == 2) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK3_LEFT);
        }
        else if (id == 3) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK4_LEFT);
        }
        else {
            return gamePlayAtlas.findRegion(RegionNames.SHARK5_LEFT);
        }
    }

    public TextureAtlas.AtlasRegion setRightShark(int id) {
        if (id == 0) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK_RIGHT);
        }
        else if (id == 1) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK2_RIGHT);
        }
        else if (id == 2) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK3_RIGHT);
        }
        else if (id == 3) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK4_RIGHT);
        }
        else {
            return gamePlayAtlas.findRegion(RegionNames.SHARK5_RIGHT);
        }
    }

    public TextureAtlas.AtlasRegion setDeadShark(int id) {
        if (id == 0) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK_DEAD);
        }
        else if (id == 1) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK2_DEAD);
        }
        else if (id == 2) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK3_DEAD);
        }
        else if (id == 3) {
            return gamePlayAtlas.findRegion(RegionNames.SHARK4_DEAD);
        }
        else {
            return gamePlayAtlas.findRegion(RegionNames.SHARK5_DEAD);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewportFont.update(width, height, true);
    }
}


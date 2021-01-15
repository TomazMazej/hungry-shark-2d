package com.feri.hungryshark2d.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.feri.hungryshark2d.HungryShark2D;
import com.feri.hungryshark2d.retrofit.GetRequest;
import com.feri.hungryshark2d.retrofit.JsonPlaceHolderApi;
import com.feri.hungryshark2d.retrofit.PostRequest;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGHSCORE = "highscore";
    public static final String USER_ID = "USER_ID";
    private static final String MUTE = "mute";
    private static final String SHARK_ID = "sharkId"; //id nazadnje zbranega sharka

    private int score;
    private int highScore;
    private int coins;
    private String idUSER;
    private PostRequest postRequest;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private int c = 0;
    private boolean mute;
    private int sharkId;

    private final Preferences prefs;

    public GameManager() {
        prefs = Gdx.app.getPreferences(HungryShark2D.class.getSimpleName());
        highScore = prefs.getInteger(HIGHSCORE, 0);
        mute = prefs.getBoolean(MUTE, false);
        sharkId = prefs.getInteger(SHARK_ID, 0);
        setAppId();
        coins = c;
    }

    public void getCoinsFromBC(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<GetRequest> call = jsonPlaceHolderApi.getPost(idUSER);

        call.enqueue(new Callback<GetRequest>() {
            @Override
            public void onResponse(Call<GetRequest> call, Response<GetRequest> response) {
                if(!response.isSuccessful()){
                    System.out.println("Code: " + response.code());
                    return;
                }
                else{
                    c = response.body().getCoins();
                    System.out.println(c);
                }
            }

            @Override
            public void onFailure(Call<GetRequest> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void postCoinsToBC(int coins){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //postRequest = new PostRequest(idUSER, coins, "");
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<PostRequest> call = jsonPlaceHolderApi.createPost(idUSER, "", coins); //poslje POST
        //System.out.println(new Gson().toJson(postRequest));

        call.enqueue(new Callback<PostRequest>() {
            @Override
            public void onResponse(Call<PostRequest> call, Response<PostRequest> response) {
                if (!response.isSuccessful()){ //če request ni uspešen
                    System.out.println("Response: neuspesno!");
                    return;
                }
                else{
                    System.out.println("Response: uspešno!");
                    System.out.println(response.body().getCoins());
                    c = response.body().getCoins();
                }
            }

            @Override
            public void onFailure(Call<PostRequest> call, Throwable t) {
                System.out.println("No response: neuspešno!");
                System.out.println(t);
            }
        });
    }

    public void setAppId() {
        if(prefs.contains(USER_ID)){ //ce ze obstaja ga preberemo
            idUSER = prefs.getString(USER_ID,"DEFAULT VALUE ERR");
        }
        else { //zgeneriramo ga prvic
            idUSER = UUID.randomUUID().toString().replace("-", "");
            prefs.putString(USER_ID, idUSER);
            prefs.flush();
        }
    }

    public int incrementScore(int amount){
        score += amount;
        if(score > highScore){
            highScore = score;
            prefs.putInteger(HIGHSCORE, highScore);
            prefs.flush();
        }
        return score;
    }

    public static BitmapFont generateFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        BitmapFont font32 = generator.generateFont(parameter); // font size 32 pixels
        generator.dispose();
        return font32;
    }

    public static float getFontSize(BitmapFont font, String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }

    public int getSharkId() {
        return sharkId;
    }

    public void setSharkId(int sharkId) {
        this.sharkId = sharkId;
        prefs.putInteger(SHARK_ID, sharkId);
        prefs.flush();
    }

    public boolean getMute() {
        return mute;
    }

    public void changeMute() {
        if(mute) {
            mute = false;
        }
        else {
            mute = true;
        }
        prefs.putBoolean(MUTE, mute);
        prefs.flush();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        postCoinsToBC(coins);
        this.coins = c;
    }
}

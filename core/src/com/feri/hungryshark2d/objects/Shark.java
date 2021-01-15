package com.feri.hungryshark2d.objects;

import com.badlogic.gdx.Gdx;

import static com.feri.hungryshark2d.config.GameConfig.SHARK_HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.SHARK_SPEED;
import static com.feri.hungryshark2d.config.GameConfig.SHARK_WIDTH;

public class Shark extends GameObjectDynamic {
    public static float sharkHealth; //Starts with 1

    public Shark() {
        super(Gdx.graphics.getWidth() / 2f, 20, SHARK_WIDTH, SHARK_HEIGHT, 0);
        this.sharkHealth = 1;
    }

    public void commandMoveLeft() {
        position.x -= SHARK_SPEED * Gdx.graphics.getDeltaTime();
        if(position.x < 0) position.x = 0;
    }

    public void commandMoveRight() {
        position.x += SHARK_SPEED * Gdx.graphics.getDeltaTime();
        if(position.x > Gdx.graphics.getWidth() - SHARK_WIDTH)
            position.x = Gdx.graphics.getWidth() - SHARK_WIDTH;
    }

    public static float getSharkHealth() {
        return sharkHealth;
    }

    public static void setSharkHealth(float sharkHealth) {
        Shark.sharkHealth = sharkHealth;
    }

}

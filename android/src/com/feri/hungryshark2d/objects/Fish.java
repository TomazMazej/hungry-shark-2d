package com.feri.hungryshark2d.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

import static com.feri.hungryshark2d.config.GameConfig.FISH_HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.FISH_SPAWN_TIME;
import static com.feri.hungryshark2d.config.GameConfig.FISH_WIDTH;

public class Fish extends GameObjectDynamic implements Pool.Poolable{
    public static long lastFishTime;
    public static final Pool<Fish> poolFish = Pools.get(Fish.class, 20);

    public Fish() {
        super(MathUtils.random(0, Gdx.graphics.getWidth() - FISH_WIDTH), Gdx.graphics.getHeight(), FISH_WIDTH, FISH_HEIGHT, FISH_SPAWN_TIME);
        this.lastFishTime = TimeUtils.nanoTime();
    }

    public static long getLastFishTime() {
        return lastFishTime;
    }

    public static void setLastFishTime(long lastFishTime) {
        Fish.lastFishTime = lastFishTime;
    }

    @Override
    public void reset() {
        this.position = new Vector2(MathUtils.random(0, Gdx.graphics.getWidth()- FISH_WIDTH), Gdx.graphics.getHeight());
    }

    public void finish(){
        poolFish.free(this);
    }
}

package com.feri.hungryshark2d.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

import static com.feri.hungryshark2d.config.GameConfig.MINE_HEIGHT;
import static com.feri.hungryshark2d.config.GameConfig.MINE_SPAWN_TIME;
import static com.feri.hungryshark2d.config.GameConfig.MINE_WIDTH;

public class Mine extends GameObjectDynamic implements Pool.Poolable{
    public static long lastMineTime;
    public static final Pool<Mine> poolMines = Pools.get(Mine.class, 20);

    public Mine() {
        super(MathUtils.random(0, Gdx.graphics.getWidth()- MINE_WIDTH), Gdx.graphics.getHeight(), MINE_WIDTH, MINE_HEIGHT, MINE_SPAWN_TIME);
        lastMineTime = TimeUtils.nanoTime();
    }

    public static long getLastMineTime() {
        return lastMineTime;
    }

    public static void setLastMineTime(long lastMineTime) {
        Mine.lastMineTime = lastMineTime;
    }

    @Override
    public void reset() {
        this.position = new Vector2(MathUtils.random(0, Gdx.graphics.getWidth()- MINE_WIDTH), Gdx.graphics.getHeight());
    }

    public void finish(){
        poolMines.free(this);
    }
}

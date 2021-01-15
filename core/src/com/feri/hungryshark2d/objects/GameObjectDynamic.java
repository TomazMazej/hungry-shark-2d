package com.feri.hungryshark2d.objects;

import com.badlogic.gdx.math.Vector2;

public class GameObjectDynamic extends GameObject {
    public final Vector2 velocity;
    public long createTime;

    public GameObjectDynamic (float x, float y, float width, float height, long createTime) {
        super(x, y, width, height);
        this.velocity = new Vector2();
        this.createTime = createTime;
    }

    public void update (float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public long getCreateTime() {
        return createTime;
    }
}

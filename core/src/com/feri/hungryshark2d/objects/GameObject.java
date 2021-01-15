package com.feri.hungryshark2d.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    public Vector2 position;
    public final Rectangle bounds;

    public GameObject (float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

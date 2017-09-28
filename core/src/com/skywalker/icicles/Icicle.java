package com.skywalker.icicles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by harshish on 28/9/17.
 */

public class Icicle {

    private static final String TAG = Icicle.class.getSimpleName();

    Vector2 position;
    Vector2 velocity;

    public Icicle(Vector2 postion) {
        this.position = postion;
        velocity = new Vector2();
    }

    public void update(float delta){
        velocity.mulAdd(Constants.ICICLES_ACCELERATION,delta);
        position.mulAdd(velocity,delta);
    }

    public void render(ShapeRenderer renderer){
        renderer.triangle(
                position.x,position.y,
                position.x - Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT,
                position.x + Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT
        );
    }

}

package com.skywalker.icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by harshish on 28/9/17.
 */

public class Player {

    private static final String TAG = Player.class.getSimpleName();

    Vector2 position;
    Viewport viewport;

    int deaths;

    public Player(Viewport viewport) {
        this.viewport = viewport;
        deaths = 0;
        init();
    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth()/2, Constants.PLAYER_HEAD_HEIGHT);
    }

    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= delta * Constants.PLAYER_MOVEMENT_SPEED;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += delta * Constants.PLAYER_MOVEMENT_SPEED;
        }

        float accelerometerInput = -Gdx.input.getAccelerometerY() / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);
        position.x += -delta * accelerometerInput * Constants.PLAYER_MOVEMENT_SPEED;

        ensureInbounds();
    }

    public boolean hitByIcicle(Icicles icicles) {
        boolean isHit = false;


        for (Icicle i: icicles.icicleList) {
            if(i.position.y <= position.y + Constants.PLAYER_HEAD_RADIUS &&
                    ((i.position.x >= position.x - Constants.PLAYER_HEAD_RADIUS) && (i.position.x <= position.x + Constants.PLAYER_HEAD_RADIUS))){
                isHit = true;
                deaths++;
            }
        }

        return isHit;
    }

    private void ensureInbounds() {
        if (position.x - Constants.PLAYER_HEAD_RADIUS < 0) {
            position.x = Constants.PLAYER_HEAD_RADIUS;
        }
        if (position.x + Constants.PLAYER_HEAD_RADIUS > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - Constants.PLAYER_HEAD_RADIUS;
        }
    }

    public void render(ShapeRenderer renderer){
        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.circle(position.x,position.y,Constants.PLAYER_HEAD_RADIUS,Constants.PLAYER_HEAD_SEGMENTS);

        Vector2 torsoTop = new Vector2(position.x,position.y - Constants.PLAYER_HEAD_RADIUS);
        Vector2 torsoBottom = new Vector2(torsoTop.x,torsoTop.y - 2 * Constants.PLAYER_HEAD_RADIUS);

        renderer.rectLine(torsoTop,torsoBottom,Constants.PLAYER_LIMB_WIDTH);

        renderer.rectLine(
                torsoTop.x,torsoTop.y,
                torsoTop.x + Constants.PLAYER_HEAD_RADIUS,torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH
        );
        renderer.rectLine(
                torsoTop.x,torsoTop.y,
                torsoTop.x - Constants.PLAYER_HEAD_RADIUS,torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH
        );

        renderer.rectLine(
                torsoBottom.x,torsoBottom.y,
                torsoBottom.x + Constants.PLAYER_HEAD_RADIUS,torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH
        );
        renderer.rectLine(
                torsoBottom.x,torsoBottom.y,
                torsoBottom.x - Constants.PLAYER_HEAD_RADIUS,torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH
        );

    }

}

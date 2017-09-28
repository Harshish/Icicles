package com.skywalker.icicles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by harshish on 28/9/17.
 */

public class Icicles {

    private static final String TAG = Icicles.class.getSimpleName();

    DelayedRemovalArray<Icicle> icicleList;
    Viewport viewport;

    int iciclesDodged;
    Constants.Difficulty difficulty;

    public Icicles(Viewport viewport, Constants.Difficulty difficulty) {
        this.difficulty = difficulty;
        this.viewport = viewport;
        init();
    }

    public void init() {
        icicleList = new DelayedRemovalArray<Icicle>(false,100);
        iciclesDodged = 0;
    }

    public void update(float delta) {
        if(MathUtils.random() < delta * difficulty.spawnRate){
            Vector2 position = new Vector2(MathUtils.random() * viewport.getWorldWidth(),viewport.getWorldHeight());
            Icicle nIcicle = new Icicle(position);
            icicleList.add(nIcicle);
        }

        for (Icicle i: icicleList)
            i.update(delta);

        icicleList.begin();

        for (int i = 0; i < icicleList.size; i++){
            if(icicleList.get(i).position.y < -Constants.ICICLES_HEIGHT) {
                iciclesDodged++;
                icicleList.removeIndex(i);
            }
        }


        icicleList.end();

    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.ICICLE_COLOR);
        for (Icicle i: icicleList)
            i.render(renderer);
    }

}

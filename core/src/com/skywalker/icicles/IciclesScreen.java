package com.skywalker.icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by harshish on 28/9/17.
 */

public class IciclesScreen extends InputAdapter implements Screen {

    private static final String TAG = IciclesScreen.class.getSimpleName();

    IciclesGame game;

    ExtendViewport viewport;
    ShapeRenderer renderer;

    Player player;
    Icicles icicles;

    ScreenViewport hudViewport;
    SpriteBatch batch;
    BitmapFont font;

    int topScore;
    Constants.Difficulty difficulty;

    public IciclesScreen(IciclesGame game,Constants.Difficulty difficulty) {
        this.difficulty = difficulty;
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(Constants.WORLD_SIZE,Constants.WORLD_SIZE);
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        player = new Player(viewport);
        icicles = new Icicles(viewport,difficulty);
       // Gdx.app.log(TAG,"HERE");
        hudViewport = new ScreenViewport();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(this);
        topScore = 0;
    }

    @Override
    public void render(float delta) {
        icicles.update(delta);
        player.update(delta);

        if(player.hitByIcicle(icicles)){
            icicles.init();
            player.init();
        }

        viewport.apply(true);
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r,Constants.BACKGROUND_COLOR.g,Constants.BACKGROUND_COLOR.b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        icicles.render(renderer);
        player.render(renderer);

        renderer.end();

        topScore = Math.max(topScore, icicles.iciclesDodged);
        hudViewport.apply();

        batch.setProjectionMatrix(hudViewport.getCamera().combined);

        batch.begin();

        font.draw(batch, "Deaths: " + player.deaths + "\nDifficulty: " + difficulty.label,
                Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN);

        font.draw(batch, "Score: " + icicles.iciclesDodged + "\nTop Score: " + topScore,
                hudViewport.getWorldWidth() - Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        player.init();
        icicles.init();

        hudViewport.update(width, height, true);
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.showDifficultyScreen();
        return true;
    }
}

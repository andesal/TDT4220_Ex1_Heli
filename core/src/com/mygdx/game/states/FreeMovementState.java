package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Helicopter;

/**
 * Created by Anders on 23.01.2018.
 */

public class FreeMovementState extends State{

    private Texture bg;
    private Texture playBtn;
    private Texture imgHeli;
    private Boolean clickedPlay = false;
    private Helicopter helicopter;
    public final static  Rectangle BOUNDS = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    public FreeMovementState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg_1280_640.png");
        playBtn = new Texture("playBtn.png");
        imgHeli = new Texture("heli1.png");
        helicopter = new Helicopter(0, 400);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            clickedPlay = true;
            playBtn.dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        helicopter.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0,0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        if (!clickedPlay) {
            sb.draw(playBtn, (MyGdxGame.WIDTH/ 2) - (playBtn.getWidth() / 2), MyGdxGame.HEIGHT / 2 );

        } else {
            sb.draw(helicopter.getSprite(), helicopter.getPosition().x, helicopter.getPosition().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        playBtn.dispose();
    }

}

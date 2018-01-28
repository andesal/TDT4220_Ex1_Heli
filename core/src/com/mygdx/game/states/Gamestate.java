package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.ControllablePlayer;
import com.mygdx.game.sprites.Player;

import java.util.Random;

/**
 * Created by Anders on 23.01.2018.
 */

public class Gamestate extends State implements InputProcessor{

    private final static int BIRDCOUNT = 5;
    private Texture bg;
    private Texture playBtn;
    private Texture imgHeli;
    private Boolean clickedPlay = false;
    public final static  Rectangle BOUNDS = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private BitmapFont font;

    private ControllablePlayer helicopter;
    private Array<Player> birds;

    public Gamestate(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg_1280_640.png");
        playBtn = new Texture("playBtn.png");
        helicopter = new ControllablePlayer("sheet_transp.png", 4, 10, 400, 0, 0, 'r', 100);
        birds = new Array<Player>();
        for (int i = 0; i <= BIRDCOUNT; i++) {
            int x = randomNumber(0, 900);
            int y = randomNumber(0, 400);
            float velX = (float) randomNumber(-100, 100) / 100;
            float velY = (float) randomNumber(-100, 100) / 100;
            if (velX < 0) {
                birds.add(new Player("sheet_bird.png", 4, x, y, velX, velY, 100));
            } else {
                birds.add(new Player("sheet_bird.png", 4, x, y, velX, velY, 100));

            }
        }
        Gdx.input.setInputProcessor(this);
        font = new BitmapFont();
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
        if (clickedPlay) {
            helicopter.update(dt);
            for (Player bird : birds) {
                bird.update(dt);
                checkCollision(helicopter, bird);
            }
            for (int i = 0; i < BIRDCOUNT; i++) {
                for (int j = 0; j < BIRDCOUNT; j++) {
                    if (birds.get(i) != birds.get(j)) {
                        checkCollision(birds.get(i), birds.get(j));

                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bg, 0,0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        if (!clickedPlay) {
            sb.draw(playBtn, (MyGdxGame.WIDTH/ 2) - (playBtn.getWidth() / 2), MyGdxGame.HEIGHT / 2 );
        }
        else {
            font.draw(sb, "x: " + helicopter.getPosition().x, 10, MyGdxGame.HEIGHT- 10);
            font.draw(sb, "y: " + helicopter.getPosition().y, 10, MyGdxGame.HEIGHT- 30);
            font.draw(sb, "Speed: " + helicopter.getSpeed(), 10, MyGdxGame.HEIGHT -50);

            TextureRegion heliFrame = helicopter.getCurrentFrame();
            float heliX = helicopter.getPosition().x;
            float heliY = helicopter.getPosition().y;
            float heliVelX = helicopter.getVelocity().x;
            float heliWidth = heliFrame.getRegionWidth();
            float heliHeight = heliFrame.getRegionHeight();
            sb.draw(heliFrame, heliVelX > 0 ? heliX : heliX + heliWidth, heliY, heliVelX < 0 ? -heliWidth : heliWidth, heliHeight);

            //Birds;
            for (int i = 0; i <= BIRDCOUNT; i++) {
                Player bird = birds.get(i);
                TextureRegion birdFrame = bird.getCurrentFrame();
                float birdVelX = bird.getVelocity().x;
                float birdX = bird.getPosition().x;
                float birdY = bird.getPosition().y;
                float birdWidth = birdFrame.getRegionWidth();
                float birdHeight = birdFrame.getRegionHeight();
                sb.draw(birdFrame, birdVelX > 0 ? birdX : birdX + birdWidth, birdY, birdVelX < 0 ? -birdWidth : birdWidth, birdHeight);
            }
        }

        sb.end();
    }

    private void checkCollision(Player player1, Player player2) {
        Rectangle bh = player1.getBounds();
        Rectangle bb = player2.getBounds();
        if (bh.overlaps(bb)) {
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(bh, bb, intersection);
            if (intersection.getWidth() > intersection.getHeight()) {
                //Top or bottom
                if (player1.getPosition().y > player2.getPosition().y) {
                    //Bottom of object1
                    player1.getVelocity().y = Math.abs(player1.getVelocity().y);
                    player2.getVelocity().y = -Math.abs(player2.getVelocity().y);

                } else {
                    //Top of object1
                    player1.getVelocity().y = -Math.abs(player1.getVelocity().y);
                    player2.getVelocity().y = Math.abs(player2.getVelocity().y);

                }
            } else if (intersection.getWidth() < intersection.getHeight()) {
                //Right or left
                if (player1.getPosition().x > player2.getPosition().x) {
                    //Right of object1
                    player1.getVelocity().x = Math.abs(player1.getVelocity().x);
                    player2.getVelocity().x = -Math.abs(player2.getVelocity().x);

                } else {
                    //Left of object1
                    player1.getVelocity().x = -Math.abs(player1.getVelocity().x);
                    player2.getVelocity().x = Math.abs(player2.getVelocity().x);

                }

            }
        }
    }

    @Override
    public void dispose() {
        playBtn.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        helicopter.changeDirection(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {return false; }


    public int randomNumber(int min, int max) {
        Random rand = new Random();
         return rand.nextInt(max - min)+ min;
    }

}
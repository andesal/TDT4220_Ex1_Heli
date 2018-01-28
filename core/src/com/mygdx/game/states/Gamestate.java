package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.ControllablePlayer;
import com.mygdx.game.sprites.Player;

import java.util.Random;

/**
 * Created by Anders on 23.01.2018.
 */

public class Gamestate extends State implements InputProcessor{

    private final static int BIRDCOUNT = 2;
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
        //Player player1 = new Player("sheet_transp.png", 4, 10, 400, (float)0.1, 0, 'r', 400, true);
        helicopter = new ControllablePlayer("sheet_transp.png", 4, 10, 400, 0, 0, 'r', 100);
        birds = new Array<Player>();
        for (int i = 0; i <= BIRDCOUNT; i++) {
            int x = randomNumber(0, 1200);
            int y = randomNumber(0, 600);
            char dir = 'r';
            float velX = (float) randomNumber(-100, 100) / 100;
            float velY = (float) randomNumber(-100, 100) / 100;
            if (velX < 0) {
                birds.add(new Player("sheet_bird.png", 4, x, y, velX, velY, 'l', 100));
            } else {
                birds.add(new Player("sheet_bird.png", 4, x, y, velX, velY, 'r', 100));

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
            for (Player bird: birds) {
                bird.update(dt);
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
            char heliDir = helicopter.getDirection();
            float heliX = helicopter.getPosition().x;
            float heliY = helicopter.getPosition().y;
            float heliWidth = heliFrame.getRegionWidth();
            float heliHeight = heliFrame.getRegionHeight();
            sb.draw(heliFrame, heliDir == 'l' ? heliX + heliWidth : heliX, heliY, heliDir == 'l' ? -heliWidth : heliWidth, heliHeight);

            //Birds;
            for (int i = 0; i <= BIRDCOUNT; i++) {
                Player bird = birds.get(i);
                TextureRegion birdFrame = bird.getCurrentFrame();
                char birdDir = bird.getDirection();
                float birdX = bird.getPosition().x;
                float birdY = bird.getPosition().y;
                float birdWidth = birdFrame.getRegionWidth();
                float birdHeight = birdFrame.getRegionHeight();
                sb.draw(birdFrame, birdDir == 'l' ? birdX + birdWidth : birdX, birdY, birdDir == 'l' ? -birdWidth : birdWidth, birdHeight);
            }
        }

        sb.end();
    }

    private void checkCollisions() {
        /*
        Bird b = birds.get(0);

        char hd = helicopter.getDirection();
        char bd = b.getDirection();
        float hx = helicopter.getPosition().x;
        float hy = helicopter.getPosition().y;
        float bx = b.getPosition().x;
        float by = b.getPosition().y;
        if (helicopter.getBounds().overlaps(b.getBounds())) {
            if (hd == 'r') {
                helicopter.setDirection('l');
                helicopter.setPos(helicopter.getPosition().x - 3, helicopter.getPosition().y);
                if (bd == 'l') {
                    b.setDirection('r');

                } else {
                }

            } else {
                helicopter.setDirection('r');
                helicopter.setPos(helicopter.getPosition().x + 3, helicopter.getPosition().y);

            }

            helicopter.setVelX(-helicopter.getVelX());
            if (helicopter.getPosition().y > b.getPosition().y) {

            }
        }

       */
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

    public boolean collides(Rectangle player1, Rectangle player2) {
        return player1.overlaps(player2);

    }

    public int randomNumber(int min, int max) {
        Random rand = new Random();
         return rand.nextInt(max - min)+ min;
    }

}
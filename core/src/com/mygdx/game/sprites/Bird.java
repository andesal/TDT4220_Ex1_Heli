package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.states.Gamestate;

/**
 * Created by anderssalvesen on 26.01.2018.
 */

public class Bird {

    private Animation animation;
    private Texture texture;
    private char direction;
    private Vector3 position;
    private float velX;
    private float velY;
    private int speed = 150;
    private Rectangle bounds;

    public Bird(int x, int y, char direction, float velX, float velY) {
        texture = new Texture("sheet_bird.png");
        animation = new Animation(new TextureRegion(texture), 4, 0.1f);
        this.direction = direction;
        this.velX = velX;
        this.velY = velY;
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, texture.getWidth()/4, texture.getHeight());


    }

    public void update(float dt) {
        animation.update(dt);
        bounds = new Rectangle(position.x, position.y, texture.getWidth()/4, texture.getHeight());
        Rectangle screenBounds = Gamestate.BOUNDS;
        if (position.x + animation.getFrame().getRegionWidth() >= screenBounds.getX() + screenBounds.getWidth() || position.x <= screenBounds.getX()) {
            direction = direction == 'r' ? 'l' : 'r';
            velX = -velX;
        }

        if (position.y <= screenBounds.getY() || position.y + animation.getFrame().getRegionHeight() >= screenBounds.getY() + screenBounds.getHeight()) {
            velY = -velY;
        }

        position.add(velX * dt * speed, velY * dt * speed, 0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public char getDirection() {
        return direction;
    }

    public TextureRegion getAnimationTexture() {
        return animation.getFrame();
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

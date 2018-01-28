package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.states.Gamestate;

/**
 * Created by anderssalvesen on 28.01.2018.
 */

public class Player {

    protected Vector3 position;
    protected Vector3 velocity;
    protected char direction;
    protected int speed;

    protected Texture texture;
    protected Animation animation;
    protected int numFrames;
    protected Rectangle bounds;
    protected boolean npc;

    public Player(String internalTexturePath, int numFrames, int startX, int startY, float velX, float velY, char startDir, int speed) {
        this.texture = new Texture(internalTexturePath);
        this.numFrames = numFrames;
        this.animation = new Animation(new TextureRegion(texture), numFrames, 0.1f);
        this.position = new Vector3(startX, startY, 0);
        this.velocity = new Vector3(velX, velY, 0);
        this.direction = startDir;
        this.speed = speed;
        this.bounds = new Rectangle(startX, startY, this.texture.getWidth()/this.numFrames, this.texture.getHeight());
    }

    public void update(float dt) {
        this.animation.update(dt);
        this.bounds = new Rectangle(position.x, position.y, texture.getWidth()/this.numFrames, texture.getHeight()); //update bounds
        checkScreenCollision(); //Check collision with screen bounds
        position.add(this.velocity.x * dt * speed, this.velocity.y * dt * speed, 0);
    }

    public void checkScreenCollision() {
        Rectangle screenBounds = Gamestate.BOUNDS;
        if (position.x + animation.getFrame().getRegionWidth() >= screenBounds.getX() + screenBounds.getWidth() || position.x <= screenBounds.getX()) {
            direction = direction == 'r' ? 'l' : 'r';
            this.velocity.x = -this.velocity.x;
        }
        if (position.y <= screenBounds.getY() || position.y + animation.getFrame().getRegionHeight() >= screenBounds.getY() + screenBounds.getHeight()) {
            this.velocity.y = -this.velocity.y;
        }
    }

    public TextureRegion getCurrentFrame() {
        return animation.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}

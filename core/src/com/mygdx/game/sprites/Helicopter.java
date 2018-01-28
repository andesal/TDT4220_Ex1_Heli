package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.states.Gamestate;

/**
 * Created by Anders on 23.01.2018.
 */

public class Helicopter {

    private Vector3 position;

    private float velX = 0;
    private float velY = 0;
    private int speed = 0;
    private char direction;
    private Texture texture;
    private Animation animation;
    private Rectangle bounds;


    public Helicopter(int x, int y) {
        texture = new Texture("sheet_transp.png");
        animation = new Animation(new TextureRegion(texture), 4, 0.1f);
        position = new Vector3(x, 400, 0);
        direction = 'r';
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


    public void setPos(float posX, float posY) {
        this.position.x = posX;
        this.position.y = posY;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void changeDirection(int screenX, int screenY) {
        screenY = (int) Gamestate.BOUNDS.getHeight() - screenY; // invert y
        if (screenX < position.x && direction == 'r') {
            direction = 'l';
            decreaseSpeed();
        } else if (screenX > position.x && direction == 'l') {
            direction = 'r';
            decreaseSpeed();
        } else {
            increaseSpeed();
        }

        Vector3 projected = new Vector3(screenX, screenY, 0);
        float pathX = projected.x - position.x;
        float pathY = projected.y - position.y;
        float distance = (float) Math.sqrt(pathX * pathX + pathY * pathY);
        velX = pathX / distance;
        velY = pathY / distance;

    }

    public Vector3 getPosition() {
        return position;
    }

    public char getDirection() {
        return direction;
    }


    private void decreaseSpeed() {
        speed = speed > 100 ? speed - 100 : speed;
    }

    private void increaseSpeed(){
        speed = speed < 1000 ? speed + 100 : speed;

    }

    public int getSpeed() {
        return speed;
    }

    public TextureRegion getTexture() {
        return animation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
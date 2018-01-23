package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.states.FreeMovementState;
import com.sun.org.apache.regexp.internal.RE;

import org.w3c.dom.css.Rect;

/**
 * Created by Anders on 23.01.2018.
 */

public class Helicopter {

    private static final int GRAVITY = 0;
    private int movement = 300;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    boolean turned = false;
    private Texture texture;
    private Sprite heliSprite;

    public Helicopter(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("he1.png");
        heliSprite = new Sprite(texture);
        bounds = new Rectangle(x, y, heliSprite.getWidth(), heliSprite.getHeight());

    }

    public void update(float dt) {
        velocity.scl(dt);
        Rectangle screenBounds = FreeMovementState.BOUNDS;
        float screenLeft = screenBounds.getX();
        float screenRight = screenLeft + screenBounds.getWidth();
        float screenBottom = screenBounds.getY();
        float screenTop = screenBottom + screenBounds.getHeight();
        System.out.println("SR " + screenRight);
        System.out.println("Px " + position.x);

        if (position.x + heliSprite.getWidth() >= screenRight || position.x <= screenLeft) {
            heliSprite.flip(true, false);
            movement = -movement;

        }
        position.add(movement * dt, velocity.y, 0);

    }

    public int getMovement(){
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return heliSprite;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

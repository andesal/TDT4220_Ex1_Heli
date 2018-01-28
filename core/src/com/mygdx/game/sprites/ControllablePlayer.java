package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.states.Gamestate;

/**
 * Created by anderssalvesen on 28.01.2018.
 */

public class ControllablePlayer extends Player{

    public ControllablePlayer(String internalTexturePath, int numFrames, int startX, int startY, float velX, float velY, char startDir, int speed) {
        super(internalTexturePath, numFrames, startX, startY, velX, velY, speed);
    }

    public void changeDirection(int screenX, int screenY) {
        screenY = (int) Gamestate.BOUNDS.getHeight() - screenY; // invert y
        if (screenX < position.x && this.velocity.x > 0) {
            decreaseSpeed();
        } else if (screenX > position.x && this.velocity.x < 0) {
            decreaseSpeed();
        } else {
            increaseSpeed();
        }

        Vector3 projected = new Vector3(screenX, screenY, 0);
        float pathX = projected.x - position.x;
        float pathY = projected.y - position.y;
        float distance = (float) Math.sqrt(pathX * pathX + pathY * pathY);
        this.velocity.x = pathX / distance;
        this.velocity.y = pathY / distance;
    }

    private void decreaseSpeed() {
        this.speed = this.speed > 100 ? this.speed - 100 : this.speed;
    }

    private void increaseSpeed(){
        this.speed = this.speed < 500 ? this.speed + 100 : this.speed;

    }

}

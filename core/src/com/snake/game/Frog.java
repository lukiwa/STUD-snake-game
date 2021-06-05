package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;


public class Frog {
    private Texture texture;
    private int textureSize;
    private int screenWidth;
    private int screenHeight;
    public Vector2 position;

    Frog(int screenWidth, int screenHeight){
        //TODO do not spawn apple on existing obstacle
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        int startX = 400;
        int startY = 100;

        texture = new Texture("frog.png");
        position = new Vector2(startX, startY);

        assert(texture.getHeight() == texture.getWidth());
        textureSize = texture.getWidth();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void move(Movement movement) {

        switch (movement) {
            case LEFT:
                position.x -= 10;
                break;
            case UP:
                position.y += 10;
                break;
            case RIGHT:
                position.x += 10;
                break;
            case DOWN:
                position.y -= 10;
                break;
        }
    }
}

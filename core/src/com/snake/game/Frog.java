package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;


public class Frog implements IMovable {
    private Texture texture;
    private int textureSize;
    private int screenWidth;
    private int screenHeight;
    private Vector2 position;

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

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void move(Movement movement) {

        switch (movement) {
            case LEFT:
                position.x -= textureSize;
                break;
            case UP:
                position.y += textureSize;
                break;
            case RIGHT:
                position.x += textureSize;
                break;
            case DOWN:
                position.y -= textureSize;
                break;
        }
    }

    public void moveToRandomPosition(){
        int x = Random.getRandomDivisibleByNumber(textureSize, screenWidth - textureSize, textureSize);
        int y = Random.getRandomDivisibleByNumber(textureSize, screenHeight - textureSize, textureSize);

        position = new Vector2(x,y);

    }
}

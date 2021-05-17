package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;


public class Apple {
    private Texture texture;
    private int textureSize;
    private int screenWidth;
    private int screenHeight;


    public Vector2 position;

    Apple(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        int startX = 300;
        int startY = 300;

        texture = new Texture("apple.png");
        position = new Vector2(startX, startY);

        assert(texture.getHeight() == texture.getWidth());
        textureSize = texture.getWidth();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
    public void moveToRandomPosition(){
        int x = Random.getRandomDivisibleByNumber(textureSize, screenWidth - textureSize, textureSize);
        int y = Random.getRandomDivisibleByNumber(textureSize, screenHeight - textureSize, textureSize);

        position = new Vector2(x,y);

    }

}

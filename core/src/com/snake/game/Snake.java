package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Vector;

enum Movement {
    LEFT, UP, RIGHT, DOWN
}

class SnakePart {
    public Texture texture;
    public Vector2 position;
    public int partSize;

    public SnakePart(Vector2 position) {
        texture = new Texture("head.png");
        this.position = position;
        assert (texture.getHeight() == texture.getWidth());

        partSize = texture.getWidth();
    }
}

public class Snake {
    private int length;
    private final int partSize;
    private int screenWidth;
    private int screenHeight;

    private Array<SnakePart> snakeParts;

    public Snake(int screenWidth, int screenHeight) {
        length = 3;
        int startX = 100;
        int startY = 100;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        SnakePart snakePart = new SnakePart(new Vector2(startX, startY));
        partSize = snakePart.partSize;

        snakeParts = new Array<>();
        snakeParts.add(snakePart);

        for (int i = 0; i < length - 1; ++i) {
            snakeParts.add(new SnakePart(new Vector2(startX + (i + 1) * partSize, startY)));
        }
    }

    public void render(SpriteBatch batch) {
        for (SnakePart snakePart : snakeParts) {
            batch.draw(snakePart.texture, snakePart.position.x, snakePart.position.y);
        }
    }

    public void move(Movement movement) {
        for (int i = length - 1; i > 0; --i) {
            snakeParts.get(i).position.x = snakeParts.get(i - 1).position.x;
            snakeParts.get(i).position.y = snakeParts.get(i - 1).position.y;
        }
        switch (movement) {
            case LEFT:
                snakeParts.get(0).position.x -= partSize;
                break;
            case UP:
                snakeParts.get(0).position.y += partSize;
                break;
            case RIGHT:
                snakeParts.get(0).position.x += partSize;
                break;
            case DOWN:
                snakeParts.get(0).position.y -= partSize;
                break;
        }
    }

    public Vector2 getHeadPosition() {
        return snakeParts.get(0).position;
    }

    public void grow() {
        System.out.println("GROW");
        snakeParts.add(new SnakePart(new Vector2(snakeParts.get(snakeParts.size - 1).position.x,
                                                 snakeParts.get(snakeParts.size - 1).position.y)));
        ++length;
    }
    public boolean checkCollision(){
        if(snakeParts.get(0).position.x == 0 || snakeParts.get(0).position.x == screenWidth ){
            return true;
        }

        if(snakeParts.get(0).position.y == 0 || snakeParts.get(0).position.y == screenHeight ){
            return true;
        }

        for (int i = 1; i < length - 1; ++i) {
            if (snakeParts.get(0).position.x == snakeParts.get(i).position.x &&
                snakeParts.get(0).position.y == snakeParts.get(i).position.y) {
                return true;
            }
        }

        return false;
    }



}

package com.snake.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.events.EventException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum Movement {
    LEFT, UP, RIGHT, DOWN
}

class SnakePart extends DrawablePart{
    public SnakePart(Vector2 position) {
        super(position);
        readTexture("head.png");
    }
}

public class Snake implements IMovable, IObstacle {
    private int length;
    private final int partSize;
    private final Lock _mutex = new ReentrantLock(true);


    private Array<SnakePart> snakeParts;

    public Snake() {
        length = 3;
        int startX = 100;
        int startY = 100;


        SnakePart snakePart = new SnakePart(new Vector2(startX, startY));
        partSize = snakePart.partSize;

        snakeParts = new Array<>();
        snakeParts.add(snakePart);

        for (int i = 0; i < length - 1; ++i) {
            snakeParts.add(new SnakePart(new Vector2(startX + (i + 1) * partSize, startY)));
        }
    }

    public Snake(int startX, int startY) {
        length = 3;


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
            snakePart.render(batch);
        }
    }

    public void move(Movement movement) {
        _mutex.lock();

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

        _mutex.unlock();
    }

    public void grow() {
        System.out.println("GROW");
        snakeParts.add(new SnakePart(new Vector2(snakeParts.get(snakeParts.size - 1).position.x,
                                                 snakeParts.get(snakeParts.size - 1).position.y)));
        ++length;
    }

    @Override
    public Vector2 getPosition() {
        return snakeParts.get(0).position;
    }

    @Override
    public boolean isCollisionDetected(IMovable movingObject) {
        //TODO below only true when movingObject is "this"

        try {
            for (int i = 1; i < length - 1; ++i) {
                _mutex.lock();

                if (movingObject.getPosition().x == snakeParts.get(i).position.x &&
                        movingObject.getPosition().y == snakeParts.get(i).position.y) {
                    throw new Exception("Collision detected");
                }

                _mutex.unlock();

            }
        }
        catch (Exception e){
            _mutex.unlock();
            return true;
        }

        return false;
    }
}

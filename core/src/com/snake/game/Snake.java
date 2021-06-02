package com.snake.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.events.EventException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Derived class from drawable part, represents one of the snake segments
 */
class SnakePart extends DrawablePart {
    public SnakePart(Vector2 position) {
        super(position);
        readTexture("head.png");
    }
}

/**
 * Player's snake
 */
public class Snake implements IMovable, IObstacle {
    private final int initialLength;
    private int length;
    private final int partSize;
    private final Lock _mutex = new ReentrantLock(true);


    private Array<SnakePart> snakeParts;

    public Snake() {
        initialLength = 3;
        length = initialLength;
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

    /**
     * Add new segment at the back of the snake, and increase length
     */
    public void grow() {
        System.out.println("GROW");
        snakeParts.add(new SnakePart(new Vector2(snakeParts.get(snakeParts.size - 1).position.x,
                snakeParts.get(snakeParts.size - 1).position.y)));
        ++length;
    }

    /**
     * Position is in fact the head of the snake - because only head can collide with other objects
     * @return
     */
    @Override
    public Vector2 getPosition() {
        return snakeParts.get(0).position;
    }

    /**
     * Points is current length - initial length
     * @return
     */
    public int getPoints() {
        System.out.println("LENGTH: " + length);
        return length - initialLength;
    }

    /**
     * Since snake can collide with other snake or with itself
     * @param movingObject moving object
     * @return true if collison is detected
     */
    @Override
    public boolean isCollisionDetected(IMovable movingObject) {

        int startingIndex = 0;
        if (movingObject == this) {
            startingIndex = 1;
        }

        try {
            if (movingObject == this) {

                for (int i = startingIndex; i < length - 1; ++i) {
                    _mutex.lock();

                    if (movingObject.getPosition().x == snakeParts.get(i).position.x &&
                            movingObject.getPosition().y == snakeParts.get(i).position.y) {
                        throw new Exception("Collision detected");
                    }

                    _mutex.unlock();

                }
            }
        } catch (Exception e) {
            _mutex.unlock();
            return true;
        }

        return false;
    }
}

package com.snake.game;

import com.badlogic.gdx.math.Vector2;

/**
 * All object able to move should implement this interface
 */
public interface IMovable {
    public Vector2 getPosition();

    public void move(Movement movement);

}

package com.snake.game;

/**
 * Interface for obstacle objects (not necessarily static object, snake is both movable and obstacle object)
 */
public interface IObstacle {
    public boolean isCollisionDetected(IMovable movingObject);
}

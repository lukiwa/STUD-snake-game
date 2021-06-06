package com.snake.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Interface for obstacle objects (not necessarily static object, snake is both movable and obstacle object)
 */
public interface IObstacle {
    public boolean isCollisionDetected(IMovable movingObject);
    public Array<Vector2> getObstaclePositions();
}

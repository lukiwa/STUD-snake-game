package com.snake.game.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.snake.game.interfaces.IGameObject;

/**
 * Interface for obstacle objects (not necessarily static object, snake is both movable and obstacle object)
 */
public interface IObstacle {
    public boolean isCollisionDetected(IGameObject subject);
    public Array<Vector2> getObstaclePositions();
}

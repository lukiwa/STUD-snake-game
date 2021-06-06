package com.snake.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Borders of the screen are actually obstacles
 */
public class WindowBorders implements IObstacle{
    private int screenWidth;
    private int screenHeight;

    public WindowBorders(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public boolean isCollisionDetected(IMovable movingObject) {
        if(movingObject.getPosition().x == 0 || movingObject.getPosition().x == screenWidth ){
            return true;
        }

        if(movingObject.getPosition().y == 0 || movingObject.getPosition().y == screenHeight ){
            return true;
        }

        return false;
    }

    @Override
    public Array<Vector2> getObstaclePositions() {
        return new Array<Vector2>();
    }
}

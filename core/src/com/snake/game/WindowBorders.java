package com.snake.game;

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
}

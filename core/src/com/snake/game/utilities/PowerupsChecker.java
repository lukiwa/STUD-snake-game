package com.snake.game.utilities;

import com.badlogic.gdx.math.Vector2;
import com.snake.game.screenobjects.snakes.Snake;
import com.snake.game.interfaces.Collectable;

public class PowerupsChecker {
    Collectable[] powerUps;
    Snake[] snakes;

    public PowerupsChecker(Snake[] snakes, Collectable[] powerUps){
        this.powerUps = powerUps;
        this.snakes = snakes;
    }

    public void checkIsCollected(){
        for (Collectable powerUp: powerUps){
            for(Snake snake: snakes){
                Vector2 powerUpPosition = powerUp.getPosition();
                Vector2 snakePosition = snake.getPosition();
                if(snakePosition.x == powerUpPosition.x && snakePosition.y == powerUpPosition.y){
                    snake.grow();
                    powerUp.moveToRandomPosition();
                }
            }
        }
    }

}

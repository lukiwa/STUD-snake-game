package com.snake.game.utilities;

import com.badlogic.gdx.math.Vector2;
import com.snake.game.screenobjects.snakes.Snake;
import com.snake.game.interfaces.Collectable;

import java.util.concurrent.atomic.AtomicBoolean;

public class PowerupsChecker implements Runnable {
    private Thread t;
    private boolean isCollected;

    private Collectable[] powerUps;
    private Snake[] snakes;

    public PowerupsChecker(Snake[] snakes, Collectable[] powerUps){
        this.powerUps = powerUps;
        this.snakes = snakes;
        this.isCollected = false;

    }

    public void checkIsCollected(){
        for (Collectable powerUp: powerUps){
            for(Snake snake: snakes){
                Vector2 powerUpPosition = powerUp.getPosition();
                Vector2 snakePosition = snake.getPosition();
                if(snakePosition.x == powerUpPosition.x && snakePosition.y == powerUpPosition.y){
                    System.out.println("POWERUP PICKED UP");
                    snake.toGrowOnNextRender();
                    powerUp.moveToRandomPosition();
                }
            }
        }
    }

    /**
     * Start thread and run "checkCollisons" until join
     */
    @Override
    public void run() {
        checkIsCollected();

        System.out.println("Collision detected and set variable - ending now");
    }

    /**
     * Start new thread
     */
    public void start() {
        if (t == null) {
            t = new Thread(this, "PowerupsChecker");
            t.start();
        }
    }

    /**
     * Join underlaying thread and terminate run loop
     */
    public void join() {

        try {
            if(t != null) {
                t.join();
                t = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

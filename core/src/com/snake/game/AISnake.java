package com.snake.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class AISnake extends Snake implements Runnable{
    private Thread t;
    private Apple apple;
    private Array<Vector2> obstaclesPostions;
    private Movement artificialMovement;

    AISnake(int startX, int startY, Apple apple, Array<Vector2> obstaclesPostions){
        super(startX, startY);
        this.apple = apple;
        this.obstaclesPostions = obstaclesPostions;
        this.artificialMovement = Movement.LEFT;
    }


    private void snakeToApple()
    {
        Vector2 snake = getPosition();

        if(snake.x == apple.position.x && snake.y < apple.position.y) {
            artificialMovement = Movement.UP;
        }
        if(snake.x == apple.position.x && snake.y > apple.position.y) {
            artificialMovement = Movement.DOWN;
        }
        if(snake.y == apple.position.y && snake.x < apple.position.x) {
            artificialMovement = Movement.RIGHT;
        }
        if(snake.y == apple.position.y && snake.x > apple.position.x) {
            artificialMovement = Movement.LEFT;
        }


        if(snake.x < apple.position.x && snake.y < apple.position.y) {
            if(artificialMovement == Movement.DOWN)
                artificialMovement = Movement.RIGHT;
            if(artificialMovement == Movement.LEFT)
                artificialMovement = Movement.UP;
        }
        if(snake.x > apple.position.x && snake.y < apple.position.y) {
            if(artificialMovement == Movement.DOWN)
                artificialMovement = Movement.LEFT;
            if(artificialMovement == Movement.RIGHT)
                artificialMovement = Movement.UP;
        }
        if(snake.x < apple.position.x && snake.y > apple.position.y) {
            if(artificialMovement == Movement.LEFT)
                artificialMovement = Movement.DOWN;
            if(artificialMovement == Movement.UP)
                artificialMovement = Movement.RIGHT;
        }
        if(snake.x > apple.position.x && snake.y > apple.position.y) {
            if(artificialMovement == Movement.UP)
                artificialMovement = Movement.LEFT;
            if(artificialMovement == Movement.RIGHT)
                artificialMovement = Movement.DOWN;
        }
    }

    public void avoidObstacle(){
        Vector2 collision = isCollision();
        if(collision != null) {
            if(artificialMovement == Movement.UP)
                artificialMovement = Movement.LEFT;
            if(artificialMovement == Movement.LEFT)
                artificialMovement = Movement.DOWN;
            if(artificialMovement == Movement.DOWN)
                artificialMovement = Movement.LEFT;
            if(artificialMovement == Movement.RIGHT)
                artificialMovement = Movement.UP;
            move(artificialMovement);
        }
    }

    public Vector2 isCollision(){
        Vector2 snake = getPosition();
        if(artificialMovement == Movement.UP) {
            for(Vector2 position : obstaclesPostions) {
                if(position.x == snake.x && position.y > snake.y) // jeśli ten warunek jest spełniony, to wąż idzie na przeszkodę
                    return position;
            }
        }
        if(artificialMovement == Movement.DOWN) {
            for(Vector2 position : obstaclesPostions) {
                if(position.x == snake.x && position.y < snake.y)
                    return position;
            }
        }
        if(artificialMovement == Movement.RIGHT) {
            for(Vector2 position : obstaclesPostions) {
                if(position.y == snake.y && position.x > snake.x)
                    return position;
            }
        }
        if(artificialMovement == Movement.LEFT) {
            for(Vector2 position : obstaclesPostions) {
                if(position.y == snake.y && position.x < snake.x)
                    return position;
            }
        }
        return null;
    }


    /**
     * Start new thread
     */
    public void start() {
        if (t == null) {
            t = new Thread(this, "CollisionDetector");
            t.start();
        }
    }

    /**
     * Join underlaying thread and terminate run loop
     */
    public void join() {
        try {
            t.join();
            t=null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        avoidObstacle();
        snakeToApple();
        move(artificialMovement);
    }
}

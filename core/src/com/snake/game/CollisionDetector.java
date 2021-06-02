package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.atomic.AtomicBoolean;

public class CollisionDetector implements Runnable{
    private Thread t;
    private boolean timeToEnd;

    private IMovable subject;
    private IObstacle[] obstacles;
    private String winnerName;
    private SnakeGame game;
    private AtomicBoolean isDetected;


    public CollisionDetector(IMovable subject, IObstacle[] obstacles, AtomicBoolean isDetected){
        this.subject = subject;
        this.obstacles = obstacles;
        this.isDetected = isDetected;
        this.timeToEnd = false;
    }

    public boolean checkCollisions(){
        for(IObstacle obstacle: obstacles){
            if(obstacle.isCollisionDetected(subject)){
                return true;
            }
        }
        return false;
    }


    @Override
    public void run() {

        while (!timeToEnd){
            if(checkCollisions()){
                isDetected.set(true);
            }
        }

        System.out.println("Collision detected and set variable - ending now");
    }
    public void start(){
        if(t == null){
            t = new Thread(this, "CollisionDetector");
            t.start();
        }
    }
    public void join(){
        timeToEnd = true;

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class CollisionDetector implements Runnable{
    private Thread t;

    private IMovable subject;
    private IObstacle[] obstacles;

    public CollisionDetector(IMovable subject, IObstacle[] obstacles){
        this.subject = subject;
        this.obstacles = obstacles;
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
        boolean isCollisionDetected = false;
        while (!isCollisionDetected){
            if(checkCollisions()){
                isCollisionDetected = true;
            }
        }

        System.out.println("Collision detected in detector");
        Gdx.app.exit();
    }
    public void start(){
        if(t == null){
            t = new Thread(this, "CollisionDetector");
            t.start();
        }
    }

}

package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class running in own thread, responsible for detecting collision between subject and given obstacles
 */
public class CollisionDetector implements Runnable {
    private Thread t;
    private boolean timeToEnd;

    private IMovable subject;
    private IObstacle[] obstacles;
    private String winnerName;
    private SnakeGame game;
    private AtomicBoolean isDetected;


    public CollisionDetector(IMovable subject, IObstacle[] obstacles, AtomicBoolean isDetected) {
        this.subject = subject;
        this.obstacles = obstacles;
        this.isDetected = isDetected;
        this.timeToEnd = false;
    }

    /**
     * Checks for collision for all obstacle
     * @return true if detected
     */
    public boolean checkCollisions() {
        for (IObstacle obstacle : obstacles) {
            if (obstacle.isCollisionDetected(subject)) {
                System.out.println( subject + " BUMPED INTO: " + obstacle);
                return true;
            }
        }
        return false;
    }

    /**
     * Start thread and run "checkCollisons" until join
     */
    @Override
    public void run() {

        while (!timeToEnd) {
            if (checkCollisions()) {
                isDetected.set(true);
            }
        }

        System.out.println("Collision detected and set variable - ending now");
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
        timeToEnd = true;

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

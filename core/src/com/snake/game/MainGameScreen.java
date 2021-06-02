package com.snake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainGameScreen implements Screen {
    private float timer = 0.08f;
    private Snake snake;
    private Apple apple;
    private Movement movement;
    private WindowBorders borders;
    private CollisionDetector collisionDetector;
    private StaticObstacle staticObstacle;
    private SnakeGame game;

    private AtomicBoolean playerHasLost;

    SpriteBatch batch;

    MainGameScreen(SnakeGame game) {
        System.out.println("CREATE");
        this.game = game;
        batch = new SpriteBatch();
        snake = new Snake();
        borders = new WindowBorders(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        staticObstacle = new StaticObstacle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 20);
        IObstacle[] obstacles = new IObstacle[]{snake, borders, staticObstacle};

        playerHasLost = new AtomicBoolean(false);

        //If detector detects collision with player snake, the AI wins
        collisionDetector = new CollisionDetector(snake, obstacles, playerHasLost);
        collisionDetector.start();


        apple = new Apple(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        movement = Movement.RIGHT;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        staticObstacle.render(batch);
        snake.render(batch);
        apple.render(batch);
        batch.end();
        updateTime(Gdx.graphics.getDeltaTime());
        readInput();
        checkApple();

        if (playerHasLost.get()) {
            System.out.println("PLAYER HAS LOST");
            collisionDetector.join();
            game.changeGameScreenToEndScreen("AI");
        }
    }

    private void checkApple() {
        Vector2 snakeHeadPosition = snake.getPosition();
        if (snakeHeadPosition.x == apple.position.x && snakeHeadPosition.y == apple.position.y) {
            apple.moveToRandomPosition();
            snake.grow();
        }
    }

    private void updateTime(float delta) {
        timer -= delta;
        if (timer <= 0) {
            timer = 0.08f;
            snake.move(movement);
        }
    }

    private void readInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (movement != Movement.RIGHT) {
                movement = Movement.LEFT;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (movement != Movement.DOWN) {
                movement = Movement.UP;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (movement != Movement.LEFT) {
                movement = Movement.RIGHT;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (movement != Movement.UP) {
                movement = Movement.DOWN;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}

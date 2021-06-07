package com.snake.game.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snake.game.*;
import com.snake.game.interfaces.Collectable;
import com.snake.game.interfaces.IObstacle;
import com.snake.game.interfaces.Movement;
import com.snake.game.screenobjects.powerups.Apple;
import com.snake.game.screenobjects.powerups.Frog;
import com.snake.game.screenobjects.obstacles.StaticObstacle;
import com.snake.game.screenobjects.obstacles.WindowBorders;
import com.snake.game.screenobjects.snakes.AISnake;
import com.snake.game.screenobjects.snakes.Snake;
import com.snake.game.utilities.CollisionDetector;
import com.snake.game.utilities.PowerupsChecker;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Main game screen - actual game
 */
public class MainGameScreen implements Screen {
    private float timer = 0.08f;
    private Snake snake;
    private AISnake artificialSnake;

    private Apple apple;
    private Frog frog;
	private Movement movement;
    private WindowBorders borders;
    private CollisionDetector playerCollisionDetector, aiCollisionDetector;
    private StaticObstacle staticObstacle;
    private SnakeGame game;
    private Array<Vector2> obstaclesPostions;
    private AtomicBoolean playerHasLost, aiHasLost;
    private PowerupsChecker powerupsChecker;

    SpriteBatch batch;

    public MainGameScreen(SnakeGame game) {
        System.out.println("CREATE");
        this.game = game;
        batch = new SpriteBatch();


        staticObstacle = new StaticObstacle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 20);
        apple = new Apple(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new StaticObstacle[]{staticObstacle});
        frog = new Frog(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        snake = new Snake(100, 100);
        movement = Movement.RIGHT;

        playerHasLost = new AtomicBoolean(false);
        aiHasLost = new AtomicBoolean(false);

        borders = new WindowBorders(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		obstaclesPostions = staticObstacle.getObstaclePositions();
        artificialSnake = new AISnake(300, 300, apple, obstaclesPostions);

        IObstacle[] playerObstacles = new IObstacle[]{snake, artificialSnake, borders, staticObstacle};
        //because of limited AI capabilities we let aiSnake bump into itself :)
        IObstacle[] aiObstacles = new IObstacle[]{snake, borders, staticObstacle};

        powerupsChecker = new PowerupsChecker(new Snake[]{snake, artificialSnake}, new Collectable[]{apple,frog});

        playerCollisionDetector = new CollisionDetector(snake, playerObstacles, playerHasLost);
        aiCollisionDetector = new CollisionDetector(artificialSnake, aiObstacles, aiHasLost);
        playerCollisionDetector.start();
        aiCollisionDetector.start();

    }


    @Override
    public void show() {

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

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        staticObstacle.render(batch);
        snake.render(batch);
        artificialSnake.render(batch);
        apple.render(batch);
        frog.render(batch);
        batch.end();
        updateTime(Gdx.graphics.getDeltaTime());
        readInput();

        powerupsChecker.checkIsCollected();

        if (playerHasLost.get()) {
            System.out.println("PLAYER HAS LOST");
            playerCollisionDetector.join();
            aiCollisionDetector.join();
            game.changeGameScreenToEndScreen("AI", snake.getPoints(), artificialSnake.getPoints());
        }
		if (aiHasLost.get()) {
			System.out.println("AI HAS LOST");
			playerCollisionDetector.join();
			aiCollisionDetector.join();
			game.changeGameScreenToEndScreen("PLAYER", snake.getPoints(), artificialSnake.getPoints());
		}
    }


    private void updateTime(float delta){
		timer -= delta;
		if(timer <= 0){
            artificialSnake.start();
            timer = 0.08f;
			snake.move(movement);
			frog.move(frog.frogPerformMoves(snake));
            artificialSnake.join();
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
}

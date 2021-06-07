package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;
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

    SpriteBatch batch;

    MainGameScreen(SnakeGame game) {
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

        checkApple(snake);
        checkApple(artificialSnake);
        checkFrog(snake);
        checkFrog(artificialSnake);

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
			frog.move(frogMove());
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





	//-----------------POINTS-GAINERS----------------------------
	private void checkApple(Snake snake) {
		Vector2 snakeHeadPosition = snake.getPosition();
		if(snakeHeadPosition.x == apple.position.x && snakeHeadPosition.y == apple.position.y ){
			apple.moveToRandomPosition();
			snake.grow();
		}
	}

	private void checkFrog(Snake snake) {
		Vector2 snakeHeadPosition = snake.getPosition();
		Vector2 frogPosition = frog.getPosition();

		if(snakeHeadPosition.x == frogPosition.x && snakeHeadPosition.y == frogPosition.y ){
			frog.moveToRandomPosition();
			//worth double
			snake.grow();
			snake.grow();
		}

	}

	private Movement frogMove() {
    	Vector2 frogPosition = frog.getPosition();
		Vector2 snakePosition = snake.getPosition();
		double distance = checkDistance(snakePosition, frogPosition);
		Vector2 location = frogPosition;
		if(location.x < 10)
			return Movement.RIGHT;
		if(location.x > 490)
			return Movement.LEFT;
		if(location.y < 10)
			return Movement.UP;
		if(location.y > 490)
			return Movement.DOWN;

		if(distance < 50) {
			Vector2 positionUp = frogPosition;
			positionUp.y += 1;
			double distanceUp = checkDistance(snakePosition, positionUp);

			Vector2 positionDown = frogPosition;
			positionDown.y -= 1;
			double distanceDown = checkDistance(snakePosition, positionDown);

			Vector2 positionLeft = frogPosition;
			positionLeft.x -= 1;
			double distanceLeft = checkDistance(snakePosition, positionLeft);

			Vector2 positionRight = frogPosition;
			positionRight.x += 1;
			double distanceRight = checkDistance(snakePosition, positionRight);

			double[] table = {distanceRight, distanceUp, distanceDown, distanceLeft};
			Arrays.sort(table);
			System.out.println(distance);
			if (table[3] == distanceUp)
				return Movement.UP;
			if (table[3] == distanceDown)
				return Movement.DOWN;
			if (table[3] == distanceLeft)
				return Movement.LEFT;
			if (table[3] == distanceRight)
				return Movement.RIGHT;
		}

		Movement[] movements = {Movement.UP, Movement.DOWN, Movement.LEFT, Movement.RIGHT};
		int i = (int)(Math.random()*3) + 1;
		return movements[i];
	}

	private double checkDistance(Vector2 snake, Vector2 item)
	{
		double distance = Math.sqrt(Math.pow((snake.x - item.x), 2) + Math.pow((snake.y - item.y), 2));
		return distance;
	}



}

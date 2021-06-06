package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import java.lang.Math;
import java.util.Arrays;
import java.util.Vector;


public class SnakeGame extends ApplicationAdapter {
	private float timer = 0.08f;
	private Snake snake, artificialSnake;
	private Apple apple;
	private Frog frog;
	private Movement movement, artificialMovement;
	private WindowBorders borders;
	private CollisionDetector collisionDetector;
	private StaticObstacle staticObstacle;
	private Array<Vector2> obstaclesPostions;
	SpriteBatch batch;

	@Override
	public void create () {
		System.out.println("CREATE");
		batch = new SpriteBatch();
		snake = new Snake();
		artificialSnake = new Snake(300, 300);
		borders = new WindowBorders(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		staticObstacle = new StaticObstacle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 20);
		IObstacle[] obstacles = new IObstacle[]{snake, borders, staticObstacle};

		collisionDetector = new CollisionDetector(snake, obstacles);
		collisionDetector.start();

		obstaclesPostions = staticObstacle.getObstacles(); // pobranie pozycji wszystkich przeszkód na planszy

		apple = new Apple(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		frog = new Frog(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		System.out.println(Gdx.graphics.getWidth());
		movement = Movement.RIGHT;
		artificialMovement = Movement.LEFT;

	}

	@Override
	public void render () {
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

		//if(collisionDetector.checkCollisions()){
		//	System.out.println("COLLISION");
		//	Gdx.app.exit();
		//}


		checkFrog();
		checkApple();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private void checkApple() {
		Vector2 snakeHeadPosition = snake.getPosition();
		Vector2 artificialSnakeHeadPosition = artificialSnake.getPosition();
		if(snakeHeadPosition.x == apple.position.x && snakeHeadPosition.y == apple.position.y ){
			apple.moveToRandomPosition();
			snake.grow();
		}
		if(artificialSnakeHeadPosition.x == apple.position.x && artificialSnakeHeadPosition.y == apple.position.y ) {
			apple.moveToRandomPosition();
			artificialSnake.grow();
		}
	}

	private void checkFrog() {
		Vector2 snakeHeadPosition = snake.getPosition();
		Vector2 artificialSnakeHeadPosition = artificialSnake.getPosition();
		if(snakeHeadPosition.x == frog.position.x && snakeHeadPosition.y == frog.position.y ){
			frog.moveToRandomPosition();
			snake.grow();
			snake.grow();
		}
		if(artificialSnakeHeadPosition.x == frog.position.x && artificialSnakeHeadPosition.y == frog.position.y ) {
			frog.moveToRandomPosition();
			artificialSnake.grow();
			artificialSnake.grow();
		}
	}

	private void updateTime(float delta){
		timer -= delta;
		if(timer <= 0){
			timer = 0.08f;
			snake.move(movement);
			avoidObstacle();
			SnakeToApple();
			frog.move(frogMove());
			artificialSnake.move(artificialMovement);
		}
	}

	private void readInput(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			if(movement != Movement.RIGHT) {
				movement = Movement.LEFT;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			if(movement != Movement.DOWN) {
				movement = Movement.UP;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			if(movement != Movement.LEFT) {
				movement = Movement.RIGHT;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			if(movement != Movement.UP) {
				movement = Movement.DOWN;
			}
		}

	}

	private void SnakeToApple()
	{
		Vector2 snake = artificialSnake.getPosition();

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


	private void avoidObstacle() {

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
		artificialSnake.move(artificialMovement);
		}
	}

	// funkcja sprawdzajaca czy wąż jest na kursie kolizyjnym z przeszkodą
	private Vector2 isCollision() {
		Vector2 snake = artificialSnake.getPosition();
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

	private Movement frogMove() {
		Vector2 snakePosition = snake.getPosition();
		double distance = checkDistance(snakePosition, frog.position);
		Vector2 location = frog.position;
		if(location.x < 10)
			return Movement.RIGHT;
		if(location.x > 490)
			return Movement.LEFT;
		if(location.y < 10)
			return Movement.UP;
		if(location.y > 490)
			return Movement.DOWN;

		if(distance < 50) {
			Vector2 positionUp = frog.position;
			positionUp.y += 1;
			double distanceUp = checkDistance(snakePosition, positionUp);

			Vector2 positionDown = frog.position;
			positionDown.y -= 1;
			double distanceDown = checkDistance(snakePosition, positionDown);

			Vector2 positionLeft = frog.position;
			positionLeft.x -= 1;
			double distanceLeft = checkDistance(snakePosition, positionLeft);

			Vector2 positionRight = frog.position;
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

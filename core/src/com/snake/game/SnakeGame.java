package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;


public class SnakeGame extends ApplicationAdapter {
	private float timer = 0.08f;
	private Snake snake, artificialSnake;
	private Apple apple;
	private Movement movement, artificialMovement;
	private WindowBorders borders;
	private CollisionDetector collisionDetector;
	private StaticObstacle staticObstacle;

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



		apple = new Apple(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
		batch.end();
		updateTime(Gdx.graphics.getDeltaTime());
		readInput();

		/*
		if(collisionDetector.checkCollisions()){
			System.out.println("COLLISION");
			Gdx.app.exit();
		}
		*/


		checkApple();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private void checkApple(){
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

	private void updateTime(float delta){
		timer -= delta;
		if(timer <= 0){
			timer = 0.08f;
			snake.move(movement);
			SnakeToApple();
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
}

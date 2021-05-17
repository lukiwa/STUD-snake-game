package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;


public class SnakeGame extends ApplicationAdapter {
	private float timer = 0.08f;
	private Snake snake;
	private Apple apple;
	private Movement movement;


	SpriteBatch batch;

	@Override
	public void create () {
		System.out.println("CREATE");
		batch = new SpriteBatch();
		snake = new Snake(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		apple = new Apple(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		movement = Movement.RIGHT;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		snake.render(batch);
		apple.render(batch);
		batch.end();
		updateTime(Gdx.graphics.getDeltaTime());

		readInput();
		if(snake.checkCollision()){
			System.out.println("EXIT");
			Gdx.app.exit();
		}


		checkApple();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private void checkApple(){
		Vector2 snakeHeadPosition = snake.getHeadPosition();
		if(snakeHeadPosition.x == apple.position.x && snakeHeadPosition.y == apple.position.y ){
			apple.moveToRandomPosition();
			snake.grow();
		}
	}

	private void updateTime(float delta){
		timer -= delta;
		if(timer <= 0){
			timer = 0.08f;
			snake.move(movement);
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
}

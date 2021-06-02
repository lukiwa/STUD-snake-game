package com.snake.game;

import com.badlogic.gdx.*;


public class SnakeGame extends Game{

	@Override
	public void create () {
		MenuScreen menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	public void changeGameScreenToMainGameScreen(){
		System.out.println("CHANGE SCREEN TO GAME");
		setScreen(new MainGameScreen(this));
	}

	public void changeGameScreenToEndScreen(String winner){
		System.out.println("WINNER: " + winner);
		setScreen(new EndScreen(this, winner));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
	}

}


package com.snake.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MenuScreen implements Screen {
    private SnakeGame game;
    private SpriteBatch batch;
    private Texture menuBackgroundTexture, playButtonTexture, exitButtonTexture;
    private Image playButton;
    private Image exitButton;
    private Stage stage;

    public MenuScreen(final SnakeGame game){
        System.out.println("CREATE MENU");

        stage= new Stage();
        this.game = game;
        batch=new SpriteBatch();


        playButtonTexture = new Texture("playButton.png");
        menuBackgroundTexture = new Texture("menuBackground.png");
        exitButtonTexture = new Texture("exitButton.png");


        playButton = new Image(playButtonTexture);
        playButton.setTouchable(Touchable.enabled);
        playButton.setX(150);
        playButton.setY(300);
        playButton.setWidth(200);
        playButton.setHeight(100);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeGameScreenToMainGameScreen();
            }
        });
        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);

        exitButton = new Image(exitButtonTexture);
        exitButton.setTouchable(Touchable.enabled);
        exitButton.setX(150);
        exitButton.setY(100);
        exitButton.setWidth(200);
        exitButton.setHeight(100);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 Gdx.app.exit();
            }

        });

        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,2,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(menuBackgroundTexture,0,0, menuBackgroundTexture.getWidth(), menuBackgroundTexture.getHeight());


        playButton.draw(batch, 1);
        exitButton.draw(batch, 1);


        batch.end();
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
    public void dispose() {

    }
}
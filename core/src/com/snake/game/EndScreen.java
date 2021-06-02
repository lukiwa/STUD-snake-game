
package com.snake.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class EndScreen implements Screen {
    private SnakeGame game;
    private SpriteBatch batch;
    private Texture menuBackgroundTexture, replayButtonTexture, exitButtonTexture;
    private Image replayButton;
    private Image exitButton;
    private Stage stage;
    private String winner;
    private BitmapFont winnerText;
    private BitmapFont playerPointsText;
    private BitmapFont aiPointsText;

    private int playerPoints;
    private int aiPoints;

    public EndScreen(final SnakeGame game, String winner, int playerPoints, int aiPoints) {
        System.out.println("CREATE MENU");

        stage = new Stage();
        this.game = game;
        this.winner = winner;
        this.playerPoints = playerPoints;
        this.aiPoints = aiPoints;


        batch = new SpriteBatch();
        winnerText = new BitmapFont();
        winnerText.getData().setScale(2f);

        playerPointsText= new BitmapFont();
        playerPointsText.getData().setScale(2f);

        aiPointsText = new BitmapFont();
        aiPointsText.getData().setScale(2f);


        replayButtonTexture = new Texture("replayButton.png");
        menuBackgroundTexture = new Texture("menuBackground.png");
        exitButtonTexture = new Texture("exitButton.png");


        replayButton = new Image(replayButtonTexture);
        replayButton.setTouchable(Touchable.enabled);
        replayButton.setX(150);
        replayButton.setY(175);
        replayButton.setWidth(200);
        replayButton.setHeight(100);
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeGameScreenToMainGameScreen();
            }
        });
        stage.addActor(replayButton);
        Gdx.input.setInputProcessor(stage);

        exitButton = new Image(exitButtonTexture);
        exitButton.setTouchable(Touchable.enabled);
        exitButton.setX(150);
        exitButton.setY(50);
        exitButton.setWidth(200);
        exitButton.setHeight(100);
        exitButton.addListener(new ClickListener() {
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
        Gdx.gl.glClearColor(1, 1, 2, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(menuBackgroundTexture, 0, 0, menuBackgroundTexture.getWidth(), menuBackgroundTexture.getHeight());

        replayButton.draw(batch, 1);
        exitButton.draw(batch, 1);

        winnerText.draw(batch, " Zwyciezca: " + winner, 150, 450);
        playerPointsText.draw(batch, " Punkty gracza: " + playerPoints, 150, 400);
        aiPointsText.draw(batch, " Punkty AI: " + aiPoints, 150, 350);

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
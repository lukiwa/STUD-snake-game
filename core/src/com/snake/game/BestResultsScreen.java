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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class BestResultsScreen implements Screen {
    private SnakeGame game;
    private SpriteBatch batch;
    private Texture menuBackgroundTexture, replayButtonTexture, exitButtonTexture;
    private Image replayButton;
    private Image exitButton;
    private Stage stage;
    private BitmapFont playerBestPointsText;
    private BitmapFont aiBestPointsText;

    private int playerBestPoints;
    private int aiBestPoints;

    BestResultsScreen(final SnakeGame game, String bestResultsFilename){
        System.out.println("CREATE BEST RESULT SCREEN");

        stage = new Stage();
        this.game = game;



        batch = new SpriteBatch();


        playerBestPointsText = new BitmapFont();
        playerBestPointsText.getData().setScale(2f);

        aiBestPointsText = new BitmapFont();
        aiBestPointsText.getData().setScale(2f);


        replayButtonTexture = new Texture("replayButton.png");
        menuBackgroundTexture = new Texture("menuBackground.png");
        exitButtonTexture = new Texture("exitButton.png");



        replayButton = new Image(replayButtonTexture);
        replayButton.setTouchable(Touchable.enabled);
        replayButton.setX(150);
        replayButton.setY(225);
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
        exitButton.setY(25);
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

        getBestResultsFromFile(bestResultsFilename);

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


        playerBestPointsText.draw(batch, "Najlepszy wynik gracza: " + playerBestPoints, 125, 425);
        aiBestPointsText.draw(batch, "Najlepszy wynik AI: " + aiBestPoints, 125, 375);

        batch.end();
    }

    public void getBestResultsFromFile(String filename){
        TreeSet<Integer> playerBestResultSet = new TreeSet<Integer>();
        TreeSet<Integer> aiBestResultSet = new TreeSet<Integer>();

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] stringValidTokens = data.split(" ");

                if(stringValidTokens[0].equals("PlayerPoints:")){
                    playerBestResultSet.add(Integer.parseInt(stringValidTokens[1]));
                }

                if(stringValidTokens[0].equals("AiPoints:")){
                    aiBestResultSet.add(Integer.parseInt(stringValidTokens[1]));
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        playerBestPoints = playerBestResultSet.last();
        aiBestPoints = aiBestResultSet.last();
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

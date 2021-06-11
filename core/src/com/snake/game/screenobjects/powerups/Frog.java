package com.snake.game.screenobjects.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.snake.game.interfaces.Movement;
import com.snake.game.utilities.Random;
import com.snake.game.screenobjects.snakes.Snake;
import com.snake.game.interfaces.Collectable;
import com.snake.game.interfaces.IMovable;

import java.util.Arrays;


public class Frog extends Collectable implements IMovable {
    private Texture texture;
    private int textureSize;
    private int screenWidth;
    private int screenHeight;
    private Vector2 position;

    public Frog(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        int startX = 400;
        int startY = 100;

        texture = new Texture("frog.png");
        position = new Vector2(startX, startY);

        assert(texture.getHeight() == texture.getWidth());
        textureSize = texture.getWidth();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    /**
     * Move to given postition
     */
    public void move(Movement movement) {

        switch (movement) {
            case LEFT:
                position.x -= textureSize;
                break;
            case UP:
                position.y += textureSize;
                break;
            case RIGHT:
                position.x += textureSize;
                break;
            case DOWN:
                position.y -= textureSize;
                break;
        }
    }

    /**
     * Move to random position when picked up
     */
    public void moveToRandomPosition(){
        int x = Random.getRandomDivisibleByNumber(textureSize, screenWidth - textureSize, textureSize);
        int y = Random.getRandomDivisibleByNumber(textureSize, screenHeight - textureSize, textureSize);

        position = new Vector2(x,y);

    }

    /**
     * Perform movement
     * @param snake nearby player snake to be avoided
     * @return where to move
     */
    public Movement frogPerformMoves(Snake snake) {
        Vector2 frogPosition = position;
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

    /**
     * Check distance between player snake and given position
     * @param snake snake position
     * @param item position of item
     * @return distance
     */
    private double checkDistance(Vector2 snake, Vector2 item)
    {
        double distance = Math.sqrt(Math.pow((snake.x - item.x), 2) + Math.pow((snake.y - item.y), 2));
        return distance;
    }
}

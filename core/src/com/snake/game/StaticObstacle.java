package com.snake.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

class ObstaclePart extends DrawablePart {
    public ObstaclePart(Vector2 position) {
        super(position);
        readTexture("head.png");
    }
}

public class StaticObstacle implements IObstacle {

    private int screenWidth;
    private int screenHeight;
    private final int partSize;
    private final int numberOfSegments;
    private Array<ObstaclePart> obstacleParts;
    private Array<Vector2> positions;

    StaticObstacle(int screenWidth, int screenHeight, int numberOfSegments) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        ObstaclePart obstaclePart = new ObstaclePart(new Vector2(0, 0));
        partSize = obstaclePart.partSize;
        obstacleParts = new Array<>();
        positions = new Array<>();
        obstacleParts.add(obstaclePart);

        this.numberOfSegments = numberOfSegments;

        for (int i = 0; i < numberOfSegments - 1; ++i) {
            int x = Random.getRandomDivisibleByNumber(partSize, screenWidth - partSize, partSize);
            int y = Random.getRandomDivisibleByNumber(partSize, screenHeight - partSize, partSize);

            obstacleParts.add(new ObstaclePart(new Vector2(x, y)));
            positions.add(new Vector2(x, y));
        }
    }

    public Array<Vector2> getObstacles() {

        return positions;
    }

    public void render(SpriteBatch batch) {
        for (ObstaclePart obstaclePart : obstacleParts) {
            obstaclePart.render(batch);
        }
    }


    @Override
    public boolean isCollisionDetected(IMovable movingObject) {
        boolean result = false;

        for (int i = 1; i <  numberOfSegments ; ++i) {
            if (movingObject.getPosition().x == obstacleParts.get(i).position.x &&
                    movingObject.getPosition().y == obstacleParts.get(i).position.y) {
                result = true;
                break;
            }
        }

        return result;
    }
}


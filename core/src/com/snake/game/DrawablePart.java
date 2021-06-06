package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class, can be used in objects that contains more than one texture eg. snake or obstacle
 */
class DrawablePart {
    public Texture texture;
    public Vector2 position;
    public int partSize;

    /**
     * Read texture from file
     * @param internalPath file with given texture
     */
    protected void readTexture(String internalPath){
        texture = new Texture(internalPath);
        assert (texture.getHeight() == texture.getWidth());
        partSize = texture.getWidth();
    }

    public DrawablePart(Vector2 position) {
        this.position = position;
    }

    /**
     * Renders part on the screen
     * @param batch batch to be rendered on
     */
    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }
}
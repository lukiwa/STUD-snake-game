package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

class DrawablePart {
    public Texture texture;
    public Vector2 position;
    public int partSize;

    protected void readTexture(String internalPath){
        texture = new Texture(internalPath);
        assert (texture.getHeight() == texture.getWidth());
        partSize = texture.getWidth();
    }

    public DrawablePart(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }
}
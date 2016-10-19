package com.addgame.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen extends ScreenAdapter {
	final BubblePop game;
	private OrthographicCamera camera;
	
	public MainMenuScreen(BubblePop game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, .2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "BUBBLE", 
				1280/2 - game.font.getBounds("BUBBLE").width/2, 
				720/2 - game.font.getBounds("BUBBLE").height/2);
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}
}

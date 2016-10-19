package com.addgame.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

public class GameOverScreen extends ScreenAdapter {
	final BubblePop game;
	private OrthographicCamera camera;
	private int score;
	private int highScore;
	private long holdStartTime;
	
	public GameOverScreen(BubblePop game, int score) {
		this.game = game;
		this.score = score;
		
		holdStartTime = TimeUtils.nanoTime();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		Preferences prefs = Gdx.app.getPreferences("HighScore");
		if (score >= prefs.getInteger("score", 0)) {
			prefs.putInteger("score", score);
		}
		prefs.flush();
		highScore = prefs.getInteger("score");
		
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, .2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Game Over! Your final score is: " + score, 
				1280/2 - game.font.getBounds("Game Over! Your final score is: " + score).width/2, 
				720/2 + game.font.getBounds("Game Over! Your final score is: " + score).height/2);
		game.font.draw(game.batch, "High Score: " + highScore, 
				1280/2 - game.font.getBounds("High Score: " + highScore).width/2,
				720/2 - game.font.getBounds("High Score: " + highScore).height * 3/2);
		game.batch.end();
		
		if (!Gdx.input.isTouched()) {
			holdStartTime = TimeUtils.nanoTime();
		}
		if (TimeUtils.nanoTime() - holdStartTime > 1000000000) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}
}

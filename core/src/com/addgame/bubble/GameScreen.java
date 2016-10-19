package com.addgame.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends ScreenAdapter {
	final BubblePop game;
	private OrthographicCamera camera;
	Texture bubbleImage;
	Sound bubblePop;
	Array<Bubble> bubbles;
	long startTime;
	long lastSpawnTime;
	private int lives;
	private int score;
	
	public GameScreen(BubblePop game) {
		this.game = game;
		startTime = TimeUtils.nanoTime();
		bubbleImage = new Texture("bubble.png");
		bubblePop = Gdx.audio.newSound(Gdx.files.internal("pop.wav"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		bubbles = new Array<Bubble>();
		spawnBubble();
		
		lives = 3;
		score = 0;
	}
	private void spawnBubble() {
		int x = MathUtils.random(1280-128);
		int y = MathUtils.random(720-128);
		bubbles.add(new Bubble(x, y));
		lastSpawnTime = TimeUtils.nanoTime();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, .2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		for (Bubble bubble : bubbles) {
			game.batch.draw(bubbleImage, bubble.getX(), bubble.getY());
		}
		game.font.draw(game.batch, "Score: " + score,
				1280 / 2 - game.font.getBounds("Score: " + score).width / 2,
				720 / 2 + game.font.getBounds("Score: " + score).height / 2);
		game.font.draw(game.batch, "Lives: " + lives, 
				1280/2 - game.font.getBounds("Lives: " + lives).width/2,
				720/2 - game.font.getBounds("Lives: " + lives).height * 3/2);
		game.batch.end();
		
		for (Bubble bubble : bubbles) {
			if (bubble.getTimeAlive() > 1000000000l) {
				bubbles.removeValue(bubble, true);
				lives--;
				if (lives <= 0) {
					game.setScreen(new GameOverScreen(game, score));
					dispose();
				}
			}
			if (Gdx.input.isTouched()) {
				Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(vec);
				if (bubble.getRect().contains(vec.x, vec.y)) { 
					bubbles.removeValue(bubble, true);
					bubblePop.play();
					score++;
				}
			}
		}
		if (TimeUtils.nanoTime() - lastSpawnTime > (2000000000l - 8000000 * ((TimeUtils.nanoTime()-startTime)/1000000000l))) {
			spawnBubble();
		}
	}
	@Override
	public void dispose() {
		bubbleImage.dispose();
		bubblePop.dispose();
	}
}

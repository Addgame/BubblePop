package com.addgame.bubble;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Bubble {
	private final Rectangle rect;
	private final long createTime;
	
	public Bubble(int x, int y) {
		rect = new Rectangle(x, y, 128, 128);
		createTime = TimeUtils.nanoTime();
	}
	public float getX() {
		return rect.x;
	}
	public float getY() {
		return rect.y;
	}
	public Rectangle getRect() {
		return rect;
	}
	public long getCreateTime() {
		return createTime;
	}
	public long getTimeAlive() {
		return TimeUtils.nanoTime() - createTime;
	}
}

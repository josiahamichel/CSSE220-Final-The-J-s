package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Main enemey that moves around and can be pushed by player and cause damage to player
 */
public class Enemy implements Collidable{
	
	
	// ✅ sprite cache (shared by ALL balls)
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	
	private int x, y, radius;
	private int dx = 1;
	private int dy = 0;
	
	public Enemy(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		loadSpriteOnce();
	}
	
	public void draw(Graphics2D g2) {
		if (sprite != null) {
		// sprite replaces the circle
		g2.drawImage(sprite, x, y, 2*radius, 2*radius, null);
		} else {
		// fallback if sprite failed to load
		g2.setColor(Color.RED);
		g2.fillOval(x, y, 2*radius, 2*radius);
		}
	}
	
	// in enemy class
	public void move() {
	  x += dx;
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;
		try {
		sprite = ImageIO.read(Enemy.class.getResource("/ui/EnemyF1.png"));
		} catch (IOException | IllegalArgumentException ex) {
		sprite = null; 
		}
	}
	
	@Override
	public void update(int worldWidth, int worldHeight) {
	// move first
	x += dx;
	y += dy;

	int diameter = radius * 2;

	// Left wall
	if (x < 0) {
	x = 0; // clamp
	dx = -dx;
	}
	// Right wall
	else if (x + diameter > worldWidth) {
	x = worldWidth - diameter;
	dx = -dx;
	}

	// Top wall
	if (y < 0) {
	y = 0;
	dy = -dy;
	}
	// Bottom wall
	else if (y + diameter > worldHeight) {
	y = worldHeight - diameter;
	dy = -dy;
	}
	}
	
	
}

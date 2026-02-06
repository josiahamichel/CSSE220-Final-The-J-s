package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Game player character needs to hold at least position, health, and movement information
 * 
 */
public class Player implements Collidable{
	private int[][] maze = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,0,0,0,0,1},
			{1,0,1,0,1,0,1,1,0,1},
			{1,0,1,0,0,0,0,1,0,1},
			{1,0,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,1,0,1},
			{1,1,1,1,1,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1},
	};

	private static final int TILE_SIZE = 64;
	private static final int ALLOWED_OVERLAP_WITH_WALLS = 0; // pixels

	// ✅ sprite cache (shared by ALL balls)
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	private int x, y, radius;
	private int step = 5;

	private int lives = 3;
	private long lastHitTimeMs = 0;
	private static final long HIT_COOLDOWN_MS = 600;

	public Player(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		loadSpriteOnce();
	}

	public void knockBack(Enemy enemy) {
		int push = 15; // this is how far they get pushed

		int playerCenterX = x + radius;
		int playerCenterY = y + radius;

		int enemyCenterX = enemy.getX() + enemy.getRadius();
		int enemyCenterY = enemy.getY() + enemy.getRadius();


		// should check if right or left
		if (playerCenterX > enemyCenterX) {
			x += push;
		} else {
			x -= push;
		}

		// looking if they are above or below
		if (playerCenterY > enemyCenterY) {
			y += push;
		} else {
			y -= push;
		}
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


	//    convert position to tile
	double x_tile = x / TILE_SIZE;
	double y_tile = y / TILE_SIZE;


	// in enemy class
	public void moveRight() {
		double x_tile = (x+step+TILE_SIZE/2-ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		double y_tile = (y+TILE_SIZE/2-ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		if (maze[(int) (Math.round(y_tile))][(int) (Math.round(x_tile))] == 0) {
			x += step;
			System.out.println("x " + x_tile + " y " + y_tile);
		}
	}

	public void moveLeft() {
		double x_tile = (x-step+ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		double y_tile = (y+TILE_SIZE/2-ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		if (maze[(int) (Math.round(y_tile))][(int) (Math.round(x_tile))] == 0) {
			x -= step;
			System.out.println("x " + x_tile + " y " + y_tile);
		}
	}

	public void moveUp() {
		double x_tile = (x+TILE_SIZE/2-ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		double y_tile = (y-step+ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		if (maze[(int) (Math.round(y_tile))][(int) (Math.round(x_tile))] == 0) {
			y -= step;
			System.out.println("x " + x_tile + " y " + y_tile);
		}
	}

	public void moveDown() {
		double x_tile = (x+TILE_SIZE/2-ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		double y_tile = (y+step+TILE_SIZE/2-ALLOWED_OVERLAP_WITH_WALLS) / TILE_SIZE;
		if (maze[(int) (Math.round(y_tile))][(int) (Math.round(x_tile))] == 0) {
			y += step;
			System.out.println("s");
			System.out.println("x " + x_tile + " y " + y_tile);
		}
	}

	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;
		try {
			sprite = ImageIO.read(Enemy.class.getResource("/assets/PlayerF1.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null; 
		}
	}

	@Override
	public void update(int worldWidth, int worldHeight) {

		int diameter = radius * 2;


		//    convert position to tile

		// Left wall
		if (x < 0) {
			x = 0;
		}

		// Right wall
		else if (x + diameter > worldWidth) {
			x = worldWidth - diameter;
		}

		// Top wall
		if (y < 0) {
			y = 0;
		}

		// Bottom wall
		else if (y + diameter > worldHeight) {
			y = worldHeight - diameter;
		}
	}

	public int getLives() {
		return lives;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}

	public boolean canTakeDamage() {
		long now = System.currentTimeMillis();
		return (now - lastHitTimeMs) >= HIT_COOLDOWN_MS;
	}

	public void takeDamage() {
		if (!canTakeDamage()) {
			return;
		}

		lives--;
		lastHitTimeMs = System.currentTimeMillis();
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, radius * 2, radius * 2);
	}



}
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

		int safeX = x;
		int safeY = y;

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

		//if X push hits a wall, undo ONLY X
		for (WallTile wall : GameModel.walls) {
			if (getBounds().intersects(wall.getBounds())) {
				x = safeX;
				break;
			}
		}

		// if Y push hits a wall undo ONLY Y
		for (WallTile wall : GameModel.walls) {
			if (getBounds().intersects(wall.getBounds())) {
				y = safeY;
				break;
			}
		}
	}


	public void draw(Graphics2D g2) {
		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, x, y, 2*radius, 2*radius, null);
			g2.setColor(Color.RED);
			g2.draw(getBounds());
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.RED);
			g2.fillOval(x, y, 2*radius, 2*radius);
		}
	}

	// in enemy class
	public void moveRight() {
		x += step;
		for (int i = 0; i < GameModel.walls.size(); i++) {
			if (getBounds().intersects(GameModel.walls.get(i).getBounds())) {
				x -= step;
				break;
			}
		}
	}

	public void moveLeft() {
		x -= step;
		for (int i = 0; i < GameModel.walls.size(); i++) {
			if (getBounds().intersects(GameModel.walls.get(i).getBounds())) {
				x += step;
				break;
			}
		}
	}

	public void moveUp() {
		y -= step;
		for (int i = 0; i < GameModel.walls.size(); i++) {
			if (getBounds().intersects(GameModel.walls.get(i).getBounds())) {
				y += step;
				break;
			}
		}
	}

	public void moveDown() {
		y += step;
		for (int i = 0; i < GameModel.walls.size(); i++) {
			if (getBounds().intersects(GameModel.walls.get(i).getBounds())) {
				y -= step;
				break;
			}
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
		Rectangle r = new Rectangle(
				x,
				y,
				radius * 2,
				radius * 2
				);
		return r;
	}

}
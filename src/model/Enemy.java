package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.Random;
/**
 * Main enemey that moves around and can be pushed by player and cause damage to player
 */
public class Enemy implements Collidable{

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
			g2.setColor(Color.RED);
			g2.draw(getBounds());
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.RED);
			g2.fillOval(x, y, 2*radius, 2*radius);
		}
	}

	// in enemy class
	public void move() {
		x += dx;
		y += dy;
		
	}

	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;
		try {
			sprite = ImageIO.read(Enemy.class.getResource("/assets/EnemyF1.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null; 
		}
	}

	@Override
	public void update(int worldWidth, int worldHeight) {
		x += dx;
		y += dy;

		int diameter = radius * 2;
		
		int flipChance = 5; // out of 1000 chance of flipping direction	
		
		Random random = new Random();	
		int randInRange = random.nextInt(1000); // flips 1 out of every 100 frames (on average)
		if (randInRange <= flipChance) {
			dx = -dx;
		} else if (randInRange >= 1000-flipChance) {
			dy = -dy;
		}

		for (int i = 0; i < GameModel.walls.size(); i++) {
		    if (getBounds().intersects(GameModel.walls.get(i).getBounds())) {
		    	x -= dx;
		    	y -= dy;
				dx = -dx;
				dy = -dy;
		        break;
		    }
		}
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

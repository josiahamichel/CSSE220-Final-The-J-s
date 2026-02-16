package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * walkable tile that allows movement
 */

public class OpenTile{
	
	protected static BufferedImage sprite = null;
	protected static boolean triedLoad = false;

	private int x, y, radius;

	public OpenTile(int x, int y, int radius) {
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
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;
		try {
			sprite = ImageIO.read(Enemy.class.getResource("/assets/OpenTile.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null; 
		}
	}
}
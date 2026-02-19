package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Main class for all types of tiles
 * walls, open paths, and exits.
 */


public abstract class Tiles {

	protected int x, y, radius;

	// each subclass will have its own sprite + load flag
	private BufferedImage sprite;
	private boolean triedLoad = false;

	public int getX() {
		return x;
	}
	public int getY() {
		return y; }
	public int getRadius() { 
		return radius; }


	public Tiles(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	protected abstract String getpng();

	public void update(int worldWidth, int worldHeight) {

	}

	public void draw(Graphics2D g2) {
		loadpng();
		if (sprite != null) {
			g2.drawImage(sprite, x, y, 2 * radius, 2 * radius, null);
		} else {
			g2.setColor(Color.RED);
			g2.fillOval(x, y, 2 * radius, 2 * radius);
		}
	}

	protected void drawDebugBounds(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, radius * 2, radius * 2);
	}

	private void loadpng() {
		if (triedLoad) return;
		triedLoad = true;

		String path = getpng();
		if (path == null) {
			sprite = null;
			return;
		}

		try {

			sprite = ImageIO.read(Tiles.class.getResource(path));
		} catch (IOException| IllegalArgumentException ex) {
			sprite = null;
		}
	}
}
package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

import model.Enemy;
import model.Player;
import model.GameModel;


public class GameComponent extends JComponent {
	private static final int TILE_SIZE = 64;
	
	
	private GameModel model;
	private Image openTileImage;
	private Image wallTileImage;
	private Image backgroundImage;
	
	private Timer timer;
	
	private int height;
	private int width;
	
	private Enemy e1 = new Enemy(80,100,20); // starting x position, starting y position, size (radius)
	
	private void loadframeImages() {
		  // These images are used for drawing the maze and background we will have to add the
		// player/enemy later but i did not know where to load them
		openTileImage = loadThis("/assets/OpenTile.png");
		wallTileImage = loadThis("/assets/WallTile.png");
	    backgroundImage = loadThis("/assets/Water Background color.png");
		
	}


	private Image loadThis(String path) {
		  try {
		    var url = getClass().getResource(path);

		    if (url == null) {
		    	//If something goes wrong i had to add some sort of catch because it was causing stuff to crash
		      System.out.println("Missing image file: " + path);
		      return null;
		    }

		    return ImageIO.read(url);

		  } catch (IOException e) {
		    System.out.println("Could not read image file: " + path);
		    return null;
		  } //same as before if it can not read the image because I had the wrong file type before
		}


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	drawBackground(g2);
    drawMaze(g2);
    e1.draw(g2);
    
	}
	
	public GameComponent(GameModel model) {
		this.model = model;
		height  = model.getCols() * TILE_SIZE;
		width = model.getRows() * TILE_SIZE;
		
		loadframeImages();
		timer = new Timer(20, e -> {
			e1.update(width, height);
			repaint();
		});
		timer.start();
	}
	
	
	private void drawMaze(Graphics2D g2) { //this is the tiles i did the maze to match the example
		int[][] maze = model.getMaze();

	    for (int row = 0; row < model.getRows(); row++) {
	      for (int col = 0; col < model.getCols(); col++) {

	        int x = col * TILE_SIZE;
	        int y = row * TILE_SIZE;

	        if (maze[row][col] == 1) {
	          drawWallTile(g2, x, y);
	        } else {
	          drawOpenTile(g2, x, y);
	        }
	      }
	    }
	 }


	private void drawOpenTile(Graphics2D g2, int x, int y) {
		if (openTileImage != null) {
		      g2.drawImage(openTileImage, x, y, TILE_SIZE, TILE_SIZE, null);
		    } else {
		      g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
		    }
		}


	private void drawWallTile(Graphics2D g2, int x, int y) {
		if (wallTileImage != null) {
		      g2.drawImage(wallTileImage, x, y, TILE_SIZE, TILE_SIZE, null);
		    } else {
		      g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		      }
		}


	private void drawBackground(Graphics2D g2) {
		if (backgroundImage == null) {
		      return;
		    }

		    int width = model.getCols() * TILE_SIZE;
		    int height = model.getRows() * TILE_SIZE;

		    for (int y = 0; y < height; y += TILE_SIZE) {
		      for (int x = 0; x < width; x += TILE_SIZE) {
		        g2.drawImage(backgroundImage, x, y, TILE_SIZE, TILE_SIZE, null);
		        }
		      }
		    }
	
}

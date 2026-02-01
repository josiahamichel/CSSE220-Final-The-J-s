package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Player {

  private int col;
  private int row;
  private int tileSize;
  private int health = 3;
  private Image sprite;

  
  public Player(int startCol, int startRow, int tileSize) {
    this.col = startCol;
    this.row = startRow;
    this.tileSize = tileSize;

    // Pretty sure it's best to load the sprite once here, not every frame.
    loadSprite();
  }

// catches for imsge failure 
  private void loadSprite() {
    try {
      var url = getClass().getResource("/assets/PlayerF1.png");

      if (url == null) {
        System.out.println("Missing player sprite: /assets/PlayerF1.png");
        sprite = null;
        return;
      }

      sprite = ImageIO.read(url);

    } catch (IOException e) {
      System.out.println("Could not read player sprite file.");
      sprite = null;
    }
  }

//moves the player based off the maze

  public void moveRight() {
    col = col + 1;
  }

  public void moveLeft() {
    col = col - 1;
  }

  public void moveUp() {
    row = row - 1;
  }

  public void moveDown() {
    row = row + 1;
  }

  public void draw(Graphics2D g2) {
    int x = col * tileSize;
    int y = row * tileSize;

    if (sprite != null) {
      g2.drawImage(sprite, x, y, tileSize, tileSize, null);
    } else {
      // If the image doesn't load, I still want something visible.
      g2.fillRect(x, y, tileSize, tileSize);
    }
  }

//will do something with these later

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  public int getHealth() {
    return health;
  }

  public void takeDamage() {
    health = health - 1;
  }
}

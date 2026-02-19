package model;

import java.awt.Graphics2D;

public class WallTile extends Tiles implements Collidable {

    public WallTile(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    protected String getpng() {
        return "/assets/WallTile.png";
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        drawDebugBounds(g2);
    }
}
package model;

import java.awt.Graphics2D;

public class ExitTile extends Tiles implements Collidable {

    private int dx = 1;
    private int dy = 0;

    public ExitTile(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    protected String getpng() {
        return "/assets/exittower.png";
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        drawDebugBounds(g2);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    @Override
    public void update(int worldWidth, int worldHeight) {
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
}

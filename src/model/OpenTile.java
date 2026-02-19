package model;

public class OpenTile extends Tiles {

    public OpenTile(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    protected String getpng() {
        return "/assets/OpenTile.png";
    }
}
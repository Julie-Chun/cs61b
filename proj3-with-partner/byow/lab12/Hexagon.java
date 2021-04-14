package byow.lab12;
import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.List;

public class Hexagon {
    private TETile tile;
    private Position topLeft;
    private Position midleft;
    private Position midright;
    private int side;

    public Hexagon(TETile tile, Position topLeft, int side) {
        this.tile = tile;
        this.topLeft = topLeft;
        this.side = side;
        this.midleft = new Position(topLeft, - (side - 1), - (side - 1));
        this.midright = new Position(midleft, side - 1, 0);
    }

    public List<Position> getHexPosititons() {
        List<Position> res = new ArrayList<>();
        int height = this.getHexHeight();
        for (int i = 0; i < height; i += 1) {
            int rowStart = getRowStart(i);
            int rowLength = getRowWidth(i);
            for (int j = 0; j < rowLength; j += 1) {
                res.add(new Position(rowStart + j, this.topLeft.getY() - i));
            }
        }
        return res;
    }

    public TETile getTile() {
        return this.tile;
    }

    public int getHexHeight() {
        return this.side * 2;
    }

    public int getHexWidth() {
        return this.getRowWidth(this.side);
    }

    public int getRowStart(int i) {
        if (i < this.side) {
            return this.topLeft.getX() - i;
        } else {
            return this.topLeft.getX() - (this.side - 1) + (i % this.side);
        }
    }


    public int getRowWidth(int r) {
        if (r < this.side) {
            return this.side + r * 2;
        } else {
            return this.side + (this.side - 1) * 2 - (r % this.side) * 2;
        }
    }

    public Position getTopLeft() {
        return this.topLeft;
    }

    public Position getMidLeft() {
        return this.midleft;
    }

    public Position getMidRight() {
        return this.midright;
    }

}

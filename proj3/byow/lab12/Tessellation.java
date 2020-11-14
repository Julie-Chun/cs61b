package byow.lab12;
import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class Tessellation {
    private static final Map<Integer, Integer> COLUMN_SIZES = Map.of(
            0, 3,
            1, 4,
            2, 5,
            3, 4,
            4, 3
    );

    private final int side;

    private final int width;

    private final int height;

    private final List<Hexagon> hexagons;

    private static final Map<Integer, TETile> tiles = Map.of(
            0, Tileset.FLOWER,
            1, Tileset.WALL,
            2, Tileset.AVATAR,
            3, Tileset.FLOOR,
            4, Tileset.GRASS,
            5, Tileset.LOCKED_DOOR,
            6, Tileset.MOUNTAIN,
            7, Tileset.SAND,
            8, Tileset.TREE,
            9, Tileset.UNLOCKED_DOOR
    );

    public Tessellation(int side) {
        this.side = side;
        Hexagon dummy = new Hexagon(null, new Position(0,0), this.side);
        this.width = dummy.getHexWidth() * 3 + this.side * 2;
        this.height = dummy.getHexHeight() * 5;

        Map<Integer, Position> startingPositions = new HashMap<>();
        int middle = this.width / 2;

        Hexagon dummy2 = new Hexagon(null, new Position(middle, this.height), this.side);
        startingPositions.put(2, dummy2.getTopLeft());

        Position position1 = new Position(dummy2.getMidLeft(), -(this.side), -1);
        Hexagon dummy1 = new Hexagon(null, position1, this.side);
        startingPositions.put(1, dummy1.getTopLeft());

        Position position3 = new Position(dummy2.getMidLeft(), -(this.side), -1);
        Hexagon dummy3 = new Hexagon(null, position3, this.side);
        startingPositions.put(3, dummy3.getTopLeft());

        Position position0 = new Position(dummy1.getMidLeft(), 1, -1);
        Hexagon dummy0 = new Hexagon(null, position0, this.side);
        startingPositions.put(0, dummy0.getTopLeft());

        Position position4 = new Position(dummy3.getMidLeft(), 1, -1);
        Hexagon dummy4 = new Hexagon(null, position4, this.side);
        startingPositions.put(4, dummy4.getTopLeft());

        this.hexagons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.addColumn(startingPositions.get(i), COLUMN_SIZES.get(i), dummy.getHexHeight());
        }

    }

    private void addColumn(Position startingPosition, int num, int height) {
        for (int i = 0; i < num; i += 1) {
            int y = startingPosition.getY() - i * height;
            Position position = new Position(startingPosition.getX(), y);
            TETile tile = tiles.get(RandomUtils.uniform(new Random(), 10));
            this.hexagons.add(new Hexagon(tile, position, this.side));
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }
}





























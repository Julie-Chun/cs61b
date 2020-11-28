package byow;

import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class RoomWorld {
    private int worldWidth;
    private int worldHeight;
    TETile[][] worldFrame;
    Tessellation test;
    Avatar ava;
    Random r;

    public RoomWorld(TETile[][] tiles, int width, int height, Random r) {
        this.r = r;
        this.worldWidth = width;
        this.worldHeight = height;
        test = new Tessellation(r, width, height);

        fillNothing(tiles);
        fillWalls(tiles, test);
        fillFloor(tiles, test);

        this.worldFrame = tiles;
    }

    private void fillWalls(TETile[][] tiles, Tessellation tess) {
        List<Position> walls = tess.getWorldWallPositions();
        for (Position p : walls) {
            int y = p.getY();
            int x = p.getX();
            tiles[x][y] = Tileset.WALL;
        }

    }

    private void fillFloor(TETile[][] tiles, Tessellation tess) {
        for (Position p : tess.getWorldFloorPositions()) {
            int y = p.getY();
            int x = p.getX();
            tiles[x][y] = Tileset.FLOOR;
        }
    }

    private void fillNothing(TETile[][] tiles) {
        for (int width = 0; width < worldWidth; width++) {
            for (int height = 0; height < worldHeight; height++) {
                tiles[width][height] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] returnWorld() {
        return worldFrame;
    }

    public Tessellation getTessellation() {
        return this.test;
    }

    public Avatar getAva() {
        return ava;
    }

    public static void main(String[] args) {
        Random r = new Random(100);
        Random r1 = new Random(100);
        Tessellation test = new Tessellation(r, 60, 40);
        Tessellation test1 = new Tessellation(r1, 60, 40);
        int element = RandomUtils.uniform(r, 0, test.getWorldFloorPositions().size());
        int element1 = RandomUtils.uniform(r1, 0, test1.getWorldFloorPositions().size());
        System.out.println(element);
        System.out.println(element1);
        for (int i = 0; i < 10; i++) {
            element = RandomUtils.uniform(r, 0, test.getWorldFloorPositions().size());
            element1 = RandomUtils.uniform(r1, 0, test1.getWorldFloorPositions().size());
            System.out.println(element + "\t" + "\t" + element1);
        }
    }

}

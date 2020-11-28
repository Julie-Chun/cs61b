package byow;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Position upperLeft;
    private Position lowerRight;
    private List<Position> wallPositions = new ArrayList<>();
    private List<Position> floorPositions = new ArrayList<>();

    public Room(Position uL, Position lR) {
        upperLeft = uL;
        lowerRight = lR;
        makeWallPositions();
        makeFloorPositions();
    }

    private void makeWallPositions() {
        int startX = upperLeft.getX();
        int startY = upperLeft.getY();
        int endX = lowerRight.getX();
        int endY = lowerRight.getY();
        int width = getRoomWidth();
        int height = getRoomHeight();
        for (int x = startX; x <= startX + width; x++) {
            for (int y = startY; y >= startY - height; y--) {
                if (x == startX || x == endX) {
                    if (y <= startY && y >= endY) {
                        this.wallPositions.add(new Position(x, y));
                    }
                } else if (y == startY || y == endY) {
                    if (x >= startX && x <= endX) {
                        this.wallPositions.add(new Position(x, y));
                    }
                }
            }
        }
    }

    private void makeFloorPositions() {
        int width = getRoomWidth();
        int height = getRoomHeight();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Position p = new Position(i + upperLeft.getX(), upperLeft.getY() - j);
                if (!wallPositions.contains(p)) {
                    floorPositions.add(new Position(i + upperLeft.getX(), upperLeft.getY() - j));
                }
            }
        }
    }

    public List<Position> getWallPositions() {
        return this.wallPositions;
    }

    public List<Position> getFloorPositions() {
        return this.floorPositions;
    }

    public int getRoomHeight() {
        return upperLeft.getY() - lowerRight.getY();
    }

    public int getRoomWidth() {
        return lowerRight.getX() - upperLeft.getX();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Iterable<Position> iterThis = this.getWallPositions();
            Iterable<Position> iterOb = ((Room) obj).getWallPositions();

            return this.getWallPositions().equals(((Room) obj).getWallPositions())
                    && this.getFloorPositions().equals(((Room) obj).getFloorPositions());
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

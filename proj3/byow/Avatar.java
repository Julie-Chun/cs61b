package byow;

public class Avatar {
    private Position currPos;
    private RoomWorld world;

    public Avatar(Position p, RoomWorld world) {
        this.currPos = p;
        this.world = world;
    }

    public Position getCurrPos() {
        return this.currPos;
    }

    public void moveUp() {
        int x = currPos.getX();
        int y = currPos.getY() + 1;
        Position p = new Position(x, y);
        if (checkInBounds(p)) {
            currPos = new Position(x, y);
        }
    }

    public void moveDown() {
        int x = currPos.getX();
        int y = currPos.getY() - 1;
        Position p = new Position(x, y);
        if (checkInBounds(p)) {
            currPos = new Position(x, y);
        }
    }

    public void moveLeft() {
        int x = currPos.getX() - 1;
        int y = currPos.getY();
        Position p = new Position(x, y);
        if (checkInBounds(p)) {
            currPos = new Position(x, y);
        }
    }

    public void moveRight() {
        int x = currPos.getX() + 1;
        int y = currPos.getY();
        Position p = new Position(x, y);
        if (checkInBounds(p)) {
            currPos = new Position(x, y);
        }
    }

    private boolean checkInBounds(Position p) {
        return world.getTessellation().getWorldFloorPositions().contains(p);
    }
}

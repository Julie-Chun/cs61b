package byow.lab12;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position (Position ref, int xOff, int yOff) {
        this(ref.getX() + xOff, ref.getY() + yOff);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}

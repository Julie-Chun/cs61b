package byow;

/* @source Lab 12 Demo */
public class Position {
    private int x;
    private int y;

    public Position(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            return this.getX() == ((Position) obj).getX() && this.getY() == ((Position) obj).getY();
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

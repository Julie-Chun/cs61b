package byow;

public class RoomTest {

    public static void main(String[] args) {
        Position uL = new Position(0, 10);
        Position lR = new Position(5, 0);
        Room r = new Room(uL, lR);
        for (Position p1 : r.getWallPositions()) {
            for (Position p2: r.getFloorPositions()) {
                if (p1.equals(p2)) {
                    System.out.println("Fail");
                    return;
                }
            }
        }
        System.out.println("Success");
    }
}

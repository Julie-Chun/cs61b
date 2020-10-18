package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> pointsList;

    public NaivePointSet(List<Point> points) {
        this.pointsList = points;
    }

    /**
     * Returns the closest point to inputted point.
     * takes Theta(N) time where N is the number of points.
     **/
    @Override
    public Point nearest(double x, double y) {
        double distance =  Double.POSITIVE_INFINITY;
        Point nearestPoint = new Point(0, 0);
        Point targetPoint = new Point(x, y);

        for (Point p : pointsList) {
            double tempdist = Point.distance(p, targetPoint);
            if (tempdist < distance) {
                distance = tempdist;
                nearestPoint = p;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }

}

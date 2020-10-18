package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    /* Reference for tree root. */
    private Node head;

    /* Idea insprired by walkthrough video. */
    private static final boolean HORIZONTAL = false;

    private class Node {
        Point point;
        Node left, right;
        boolean direction;

        Node(Point p) {
            point = p;
            left = null;
            right = null;
            direction = false;
        }
    }

    public KDTree(List<Point> pList) {
        head = null;
        for (Point p: pList) {
            head = insert(p);
        }
    }

    /**
     * Returns the closest point to the inputted coordinates.
     * Takes average runtime is Big O logN, where N is the number of points.
     */
    @Override
    public Point nearest(double x, double y) {
        Node n = nearestHelper(head, new Point(x, y), head);
        return n.point;
    }

    /**
     * helper of nearest function that goes through tree to get nearest point to target.
     * inspired from lecture 19 slides psuedocode.
     **/
    private Node nearestHelper(Node n, Point target, Node nearest) {
        if (n == null) {
            return nearest;
        }
        if (Point.distance(target, n.point) < Point.distance(target, nearest.point)) {
            nearest = n;
        }

        Node goodSide = n.right;
        Node badSide = n.left;

        if (lessThan(target, n)) {
            goodSide = n.left;
            badSide = n.right;
        }

        nearest = nearestHelper(goodSide, target, nearest);

        Point bestBadPoint = bestPoint(n, target, nearest);

        if (Point.distance(bestBadPoint, target) < Point.distance(nearest.point, target)) {
            nearest = nearestHelper(badSide, target, nearest);
        }

        return nearest;
    }

    /* helper to nearestHelper to determine good and bad side. */
    private boolean lessThan(Point target, Node node) {
        if (node.direction == HORIZONTAL) {
            return target.getX() < node.point.getX();
        }
        return target.getY() < node.point.getY();
    }

    /**
     * helper to nearestHelper to determine closest Node on badSide to target.
     * inspired by walkthough slides and/or lecutre 19 slides.
     **/
    private Point bestPoint(Node parent, Point target, Node currBest) {
        if (parent.direction == HORIZONTAL) {
            return new Point(parent.point.getX(), target.getY());
        } else {
            return new Point(target.getX(), parent.point.getY());
        }
    }

    /* Returns the head node with the inserted Point p. */
    private Node insert(Point p) {
        return insertHelper(p, head, null);
    }

    /* helper to nearest method with parameter Node n and Node parent. */
    private Node insertHelper(Point p, Node n, Node parent) {
        if (n == null) {
            Node node = new Node(p);

            if (parent == null) {
                node.direction = HORIZONTAL;
            } else {
                node.direction = !parent.direction;
            }

            return node;
        }

        if (n.direction == HORIZONTAL) {
            int cmp = Double.compare(p.getX(), n.point.getX());

            if (cmp < 0) {
                n.left = insertHelper(p, n.left, n);
            } else if (cmp >= 0) {
                n.right = insertHelper(p, n.right, n);
            }

        }

        if (n.direction != HORIZONTAL) {
            int cmp = Double.compare(p.getY(), n.point.getY());

            if (cmp < 0) {
                n.left = insertHelper(p, n.left, n);
            } else if (cmp >= 0) {
                n.right = insertHelper(p, n.right, n);
            }
        }

        return n;
    }

    /* Basic sanity check for constructor and nearest sourced from walkthrough video. */
    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(4, 2);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree tree = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point x = tree.nearest(0, 7);
    }
}

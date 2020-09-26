/**
 * TODO: Fill in the add and floor methods.
 */
public class AListFloorSet implements Lab5FloorSet {
    AList<Double> items;

    public AListFloorSet() {
        items = new AList<>();
    }

    public void add(double x) {
        items.addLast(x);
    }

    public double floor(double x) {
        double largest = -99999.9;

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) <= x && items.get(i) > largest) {
                largest = items.get(i);
            }
        }

        if (-99999.9 == largest) {
            return Double.NEGATIVE_INFINITY;
        }

        return largest;
    }
}

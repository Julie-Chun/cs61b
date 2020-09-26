import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet aLFS = new AListFloorSet();
        RedBlackFloorSet rBFSF = new RedBlackFloorSet();

        for (int i = 0; i < 1000000; i++) {
            double random = StdRandom.uniform(-5000.0, 5000.0);
            aLFS.add(random);
            rBFSF.add(random);
        }

        for (int i = 0; i < 100000; i++) {
            double random = StdRandom.uniform(-5000.0, 5000.0);
            assertEquals(aLFS.floor(random), rBFSF.floor(random), 0.000001);
        }

    }
}

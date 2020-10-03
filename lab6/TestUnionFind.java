import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class TestUnionFind {
    static UnionFind u = new UnionFind(16);

    @Test
    public void testUFConnect() {
        u.connect(0, 1);
        u.connect(15, 0);
        u.connect(2, 5);
        u.connect(12, 7);
        u.connect(2, 7);
        u.connect(3, 0);
        u.connect(15, 5);
        u.connect(4, 3);

        int[] expected = new int[]{1, 7, 5, 1, 7, 7, -1, -9, -1, -1, -1, -1, 7, -1, -1, 1};
        System.out.println(Arrays.toString(u.parent));
        System.out.println(Arrays.toString(expected));
    }


}

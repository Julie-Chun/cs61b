import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static OffByOne obo = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(obo.equalChars('&', '%'));
        assertTrue(obo.equalChars('a', 'b'));
        assertFalse(obo.equalChars('a', 'z'));
        assertFalse(obo.equalChars('d', 'o'));
        assertFalse(obo.equalChars('a', 'e'));
        assertFalse(obo.equalChars('z', 'a'));
        assertFalse(obo.equalChars('A', 'a'));
        assertFalse(obo.equalChars('A', 'A'));
        assertTrue(obo.equalChars('A', 'B'));
        assertFalse(obo.equalChars('a', 'a'));
    }
}

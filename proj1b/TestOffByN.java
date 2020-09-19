import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy5 = new OffByN(5);
    static CharacterComparator offBy10 = new OffByN(10);

    @Test
    public void testOffByN() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));
        assertFalse(offBy5.equalChars('A', 'Y'));
        assertFalse(offBy5.equalChars('$', '%'));
        assertTrue(offBy5.equalChars('#', '('));
        assertTrue(offBy5.equalChars('A', 'F'));
        assertFalse(offBy5.equalChars('A', 'f'));

        assertTrue(offBy10.equalChars('a', 'k'));
        assertTrue(offBy10.equalChars('k', 'a'));
        assertFalse(offBy10.equalChars('f', 'm'));
        assertFalse(offBy10.equalChars('A', 'G'));
        assertFalse(offBy10.equalChars('$', '-'));
        assertTrue(offBy10.equalChars('!', '+'));
        assertTrue(offBy10.equalChars('A', 'K'));
        assertFalse(offBy10.equalChars('A', 'k'));
        assertTrue(offBy10.equalChars('Z', 'd'));
    }
}

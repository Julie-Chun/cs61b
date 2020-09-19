import org.junit.Test;
import static org.junit.Assert.*;
/** @author juliechun
 */

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'z'));
        assertFalse(offByOne.equalChars('d', 'o'));
        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('z', 'a'));
        assertFalse(offByOne.equalChars('A', 'a'));
        assertFalse(offByOne.equalChars('A', 'A'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertFalse(offByOne.equalChars('a', 'a'));
    }
}

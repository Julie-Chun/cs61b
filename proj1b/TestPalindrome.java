import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();
    static OffByOne obo = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);

        Deque de = palindrome.wordToDeque("");
        String a = "";
        for (int i = 0; i < "".length(); i++) {
            a += de.removeFirst();
        }
        assertEquals("", a);

        Deque deq = palindrome.wordToDeque("hello there");
        String ac = "";
        for (int i = 0; i < "hello there".length(); i++) {
            ac += deq.removeFirst();
        }
        assertEquals("hello there", ac);

        Deque dq = palindrome.wordToDeque("hm...");
        String act = "";
        for (int i = 0; i < "hm...".length(); i++) {
            act += dq.removeFirst();
        }
        assertEquals("hm...", act);

        Deque d1 = palindrome.wordToDeque("racecar");
        String a1 = "";
        for (int i = 0; i < "racecar".length(); i++) {
            a1 += d1.removeFirst();
        }
        assertEquals("racecar", a1);

        Deque d2 = palindrome.wordToDeque("noon");
        String a2 = "";
        for (int i = 0; i < "noon".length(); i++) {
            a2 += d2.removeFirst();
        }
        assertEquals("noon", a2);

        Deque d3 = palindrome.wordToDeque("aba");
        String a3 = "";
        for (int i = 0; i < "aba".length(); i++) {
            a3 += d3.removeFirst();
        }
        assertEquals("aba", a3);

    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("Aa"));
        assertTrue(palindrome.isPalindrome("AaA"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertTrue(palindrome.isPalindrome("$%%$"));
        assertTrue(palindrome.isPalindrome("$m$"));
        assertFalse(palindrome.isPalindrome("$mm&"));
        assertFalse(palindrome.isPalindrome(null));
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertFalse(palindrome.isPalindrome("flakf", obo));
        assertTrue(palindrome.isPalindrome("&a%", obo));
        assertTrue(palindrome.isPalindrome("AsB", obo));
        assertTrue(palindrome.isPalindrome("C&B", obo));
        assertFalse(palindrome.isPalindrome("AaA", obo));
        assertFalse(palindrome.isPalindrome("abba", obo));
        assertFalse(palindrome.isPalindrome("%b%", obo));
        assertTrue(palindrome.isPalindrome("abb", obo));
        assertTrue(palindrome.isPalindrome("%b&", obo));
        assertFalse(palindrome.isPalindrome("#$&", obo));
        assertFalse(palindrome.isPalindrome("aa", obo));
        assertFalse(palindrome.isPalindrome("FF", obo));
        assertFalse(palindrome.isPalindrome("AD", obo));

    }
}

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

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
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("rancor"));
    }
}
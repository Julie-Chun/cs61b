/**
 * A Palindrome class that checks if deques are a palindrome.
 * @author juliechun
 */

public class Palindrome {
    /** returns a deque with each letter of the String in order as char.
     * @param word is the input string.
     * */
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> link = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            link.addLast(c);
        }
        return link;
    }

    /** return true if word is a palindrome.
     * @param word is the input String.
     * */
    public boolean isPalindrome(String word) {
        if (word != null) {
            Deque<Character> deq = wordToDeque(word);
            int len = word.length();

            if (len <= 1 && len >= 0) {
                return true;
            } else {
                char first = deq.removeFirst();
                char last = deq.removeLast();

                if (last != first) {
                    return false;
                }

                return isPalindrome(word.substring(1, word.length() - 1));
            }
        }
        return false;
    }

    /** return true if word is considered a palindrome according to cc.
     * @param cc  is a CharacterComparator that decides what is a palindrome.
     * @param word is the input String.
     * */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }

        if (cc == null) {
            return isPalindrome(word);
        }

        Deque<Character> d = wordToDeque(word);
        int len = word.length();

        if (len == 1 || len == 0) {
            return true;
        } else {
            char first = d.removeFirst();
            char last = d.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
            return isPalindrome(word.substring(1, word.length() - 1), cc);
        }
    }

}

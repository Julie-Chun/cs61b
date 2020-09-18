/**
 * A Palindrome class that checks if deques are a palindrome.
 * @author juliechun
 */

public class Palindrome {
    /** returns a deque with each letter of the String in order as char. */
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> link = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); //charAt learned from beginnerbook.com
            link.addLast(c);
        }
        return link;
    }

    /** return true if word is a palindrome. */
    public boolean isPalindrome(String word) {
        Deque<Character> deq = wordToDeque(word);
        int len = word.length();

        if (word != null) {
            if (len <= 1 && len >= 0) {
                return true;
            } else {
                char first = deq.removeFirst();
                char last = deq.removeLast();
                if (last != first) {
                    return false;
                }
                return isPalindrome(word.substring(1,word.length() - 1)); //learned substring method from beginnerbook.com
            }
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        return false;
    }

}

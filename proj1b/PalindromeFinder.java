/**
 * @author cs61bstaff
 * This class outputs all palindromes in the words file.
 * */

public class PalindromeFinder {

    /** @param args */
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-fa20/data/words.txt");
        Palindrome palindrome = new Palindrome();
        OffByOne obo = new OffByOne();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength
                    && palindrome.isPalindrome(word, obo)) {
                System.out.println(word);
            }
        }


    }
}

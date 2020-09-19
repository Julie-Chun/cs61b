/**
 * @author juliechun
 */
public class OffByN implements CharacterComparator {
    /** creates a variable for the number off by. */
    public int off;

    /** takes int n as argument and saves it as an instance variable off.
     * @param n is the input of the number off by.
     * */
    public OffByN(int n) {
        off = n;
    }

    @Override
    public boolean equalChars(char a, char b) {
        int diff = Math.abs(a - b);

        return (diff == off);
    }



}

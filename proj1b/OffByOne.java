/**
 * @author juliechun
 */
public class OffByOne implements CharacterComparator {
    /** returns true if two characters are off by one. */

    @Override
    public boolean equalChars(char x, char y) {

        int diff = Math.abs(x - y);
        return (diff == 1);
    }


}

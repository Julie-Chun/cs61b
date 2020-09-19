/**
 * @author juliechun
 */
public interface CharacterComparator {

    /** Returns true if chars are equal by the rules of the implementing class.
     * @param x is a char input for comparison.
     * @param y is a char input for comparison.
     */
    boolean equalChars(char x, char y);
}

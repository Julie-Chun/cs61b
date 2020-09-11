/** An Integer tester created by Flik Enterprises.
 * @author Josh Hug
 * */
public class Flik {
    /** @param a Value 1
     *  @param b Value 2
     *  @return Whether a and b are the same */
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b);
        /**the bug was caused by the difference between the = function
         * and the .equals function. one of them checks for if the values
         * are equivalent while the other checks to see if the actual
         * object is the same.
         */

    }
}

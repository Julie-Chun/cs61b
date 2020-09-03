/**
 *	Tests Body Class
 */

public class TestBody{
	public static void main (String[] args){
		checkCalcForceExertedBy();
	}
	/**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
    
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Double.isNaN(actual) || Double.isInfinite(actual)) {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
            System.out.println("PASS: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        }
    }

	public static void checkCalcForceExertedBy(){
		System.out.println("Checking calcExertedForceBy...");

        Body b1 = new Body(-10.0, 1.3, -5, -124, 10e3, "one.gif");
        Body b2 = new Body(12.0, 3.7, -245, -99, 4e4, "two.gif");
        Body b3 = new Body(5.0, -3.2, -894, 256, 2e9, "three.gif");

        checkEquals(b1.calcForceExertedBy(b2), ((0.0000000000667*(10e3)*(4e4))/(((-10.0-12.0)*(-10.0-12.0))+((1.3-3.7)*(1.3-3.7)))), "calcForceExertedBy(Body b2)", 0.01);
        checkEquals(b1.calcForceExertedBy(b3), ((0.0000000000667*(10e3)*(2e9))/(((-10.0-5.0 )*(-10.0-5.0 ))+((1.3+3.2)*(1.3+3.2)))), "calcForceExertedBy(Body b3)", 0.01);
	}

}













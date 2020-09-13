/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");

		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);

	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");


		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);

	}

	/* Made three tests to test addFirst, addLast, printDeque, and both of the get methods.

	public static boolean checkAddedFirst(int expected, int actual) {
		if (expected != actual) {
			System.out.println("addFirst(T item) returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	public static void addFirstTest(){

		System.out.println("Running addFirst test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

		lld1.addFirst(1);
		boolean passed = checkAddedFirst(1, (int) lld1.first.value);

		lld1.addFirst(0);
		passed = checkAddedFirst(0, (int) lld1.first.value) && passed;

		lld1.addFirst(-1);
		passed = checkAddedFirst(-1, (int) lld1.first.value) && passed;

		lld1.printDeque();
		printTestStatus(passed);

	}

	public static boolean checkAddedLast(int expected, int actual) {
		if (expected != actual) {
			System.out.println("addFirst(T item) returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	public static void addLastTest(){

		System.out.println("Running addFirst test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

		lld1.addLast(10);
		boolean passed = checkAddedLast(10, (int) lld1.last.value);

		lld1.addLast(4);
		passed = checkAddedFirst(4, (int) lld1.last.value) && passed;

		lld1.addLast(-99);
		passed = checkAddedFirst(-99, (int) lld1.last.value) && passed;

		lld1.printDeque();
		printTestStatus(passed);

	}

	public static boolean checkGet(int expected, int actual) {
		if (expected != actual) {
			System.out.println("get(int index) returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	public static void getTest(){

		System.out.println("Running get test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

		lld1.addLast(1);
		lld1.addLast(4);
		lld1.addLast(0);
		lld1.addLast(17);
		lld1.addLast(10);
		lld1.addLast(45);

		boolean passed = checkGet(1, (int) lld1.get(0));

		passed = checkGet(45, (int) lld1.get(lld1.size() - 1)) && passed;

		passed = checkGet(0, lld1.get(2)) && passed;

		lld1.printDeque();
		printTestStatus(passed);

	}


	public static boolean checkGetRecursive(int expected, int actual) {
		if (expected != actual) {
			System.out.println("getRecursive(int index) returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	public static void getRecursiveTest(){

		System.out.println("Running getRecursive test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

		lld1.addLast(5);
		lld1.addLast(2);
		lld1.addLast(47);
		lld1.addLast(53);
		lld1.addLast(-10);
		lld1.addLast(8);

		//testing the get
		boolean passed = checkGetRecursive(5, (int) lld1.getRecursive(0));

		passed = checkGetRecursive(8, (int) lld1.getRecursive(lld1.size() - 1)) && passed;

		passed = checkGetRecursive(47, lld1.getRecursive(2)) && passed;

		//testing if first is back to its original place.
		passed = checkGetRecursive(5, (int) lld1.first.value) && passed;

		//checking the size
		passed = checkGetRecursive(6, lld1.size()) && passed;

		lld1.printDeque();
		printTestStatus(passed);

	}

	 */

	public static boolean checkaddFirstAD(int expected, int actual) {
		if (expected != actual) {
			System.out.println("addFirstAD(T item) returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	public static void addFirstADTest(){

		System.out.println("Running addFirstAD test.");

		ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

		//testing the addFirst of ArrayDeque
		boolean passed = checkaddFirstAD(4, lld1.nextFirst); //checks is nextFirst is in the right place
		passed = checkaddFirstAD(5, lld1.nextLast) && passed;//checks is nextLast is in the right place

		lld1.addLast(5);
		lld1.addLast(2);
		lld1.addFirst(47);
		lld1.addLast(53);
		lld1.addFirst(-10);
		lld1.addLast(8);
		lld1.addFirst(9);
		lld1.addFirst(21);
		lld1.addFirst(14);
		lld1.removeFirst();
		lld1.removeLast();


		passed = checkaddFirstAD(1, lld1.nextFirst) && passed;//checks is nextFirst is in the right place
		passed = checkaddFirstAD(9, lld1.nextLast) && passed;
		passed = checkaddFirstAD(-10, lld1.get(4)) && passed;
		passed = checkaddFirstAD(7, lld1.size) && passed;//checking the size

		//checking if ArrayDeque keeps the usage factor.
		lld1.addFirst(0);
		lld1.addFirst(76);
		lld1.addLast(25);
		lld1.addFirst(90);
		lld1.addFirst(35);
		lld1.addLast(77);
		lld1.addLast(42);
		lld1.addLast(44);
		lld1.addLast(23);
		lld1.addLast(33);
		lld1.removeFirst();
		lld1.removeFirst();
		lld1.removeLast();
		lld1.removeFirst();
		lld1.removeLast();
		lld1.removeLast();
		lld1.removeFirst();
		lld1.removeLast();
		lld1.removeFirst();
		lld1.removeLast();
		lld1.removeLast();
		lld1.removeFirst();
		lld1.removeLast();

		lld1.printDeque(); //can check if array is printed correctly
		printTestStatus(passed);

	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		//addIsEmptySizeTest();
		//addRemoveTest();
		//addFirstTest();
		//addLastTest();
		//getTest();
		//getRecursiveTest();
		//addFirstADTest();
	}
} 
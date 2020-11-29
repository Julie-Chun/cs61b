/** @author juliechun
 *
 * */
public class ArrayDeque<T> {

    /** @param size is the size of the array inputs. */
    private int size;

    /** @param items is the items of the array. */
    private T[] items;

    /** @param nextFirst is the size of the list inputs. */
    private int nextFirst;

    /** nextLast indicates the index of where the next items should go
     * if added to the end fo the list.
     */
    private int nextLast;

    /** initalizes the array with a size, items list.
     * nextFirst position, and nextLast position reference.
     * */
    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = items.length / 2;
        nextLast = nextFirst + 1;
    }

    /** adds @param item to the front of the list. */
    public void addFirst(T item) {
        items[nextFirst] = item;
        size++;
        nextFirst--;

        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }

        if (size > items.length - 1) {
            resizeUp();
        }

    }
    /** adds @param item to the end of the list. */
    public void addLast(T item) {
        items[nextLast] = item;
        size++;
        nextLast++;

        if (nextLast == items.length) {
            nextLast = 0;
        }

        if (size > items.length - 1) {
            resizeUp();
        }
    }

    /** rescales the list so that its usage factor is more
     * than 25% and the nextFront and nextLast has a space in the list. */
    private void resize() {
        if (nextFirst < 0) {
            T[] larger = (T[]) new Object[items.length + 1];
            System.arraycopy(items, 0, larger, nextFirst + 2, items.length - 1);
            items = larger;
            nextFirst++;
            nextLast++;
        }

        if (nextLast >= items.length) {
            T[] larger = (T[]) new Object[items.length + 1];
            System.arraycopy(items, 0, larger, 0, items.length);
            items = larger;
        }

        if (items.length > 15) {
            double usageFactor = (double) size / (double) items.length;
            if (usageFactor < 0.25) {
                T[] smaller = (T[]) new Object[size + 2];
                System.arraycopy(items, nextFirst, smaller, 0, size + 2);
                items = smaller;
                nextLast = size + 1;
                nextFirst = 0;
            }
        }

    }

    /** rescales the deque to twice its size once it is full. */
    private void resizeUp() {
        int first = nextFirst - 1;
        int last = nextLast + 1;

        if (nextLast == 0) {
            last = items.length - 1;
        }

        if (nextFirst == items.length - 1) {
            first = 0;
        }

        if (first < last) {
            T[] holder = slice(items, first, last);
            T[] copy = (T[]) new Object[items.length * 2];
            System.arraycopy(holder, 0, copy, first, holder.length);
            items = holder;
        } else if (first > last) {
            T[] front = slice(items, 0, last);
            T[] back = slice(items, first, items.length - 1);
            T[] copy = (T[]) new Object[items.length * 2];
            System.arraycopy(front, 0, copy, 0, front.length);
            System.arraycopy(back, 0, copy, first, back.length);
            items = merge(front, back);
        }

        System.out.println("resize UP");
    }

    /** rescales the list so that its usage factor is more than 25%. */
    private void resizeDown() {
        int first = nextFirst - 1;
        int last = nextLast + 1;

        if (nextLast == 0) {
            last = items.length - 1;
        }

        if (nextFirst == items.length - 1) {
            first = 0;
        }

        if (first < last) {
            T[] holder = slice(items, first, last);
            T[] copy = (T[]) new Object[items.length / 2];
            System.arraycopy(holder, 0, copy, first, holder.length);
            items = holder;
        } else if (first > last) {
            T[] front = slice(items, 0, last);
            T[] back = slice(items, first, items.length - 1);
            T[] copy = (T[]) new Object[items.length / 2];
            System.arraycopy(front, 0, copy, 0, front.length);
            System.arraycopy(back, 0, copy, first, back.length);
            items = merge(front, back);
        }

        System.out.println("resize Down");
    }
    /** @return true if the array list is empty. */
    public boolean isEmpty() {
        for (int i = nextFirst + 1; i < size + nextFirst + 1; i++) {
            if (items[i] != null) {
                return false;
            }
        }
        return true;
    }

    /** returns the int size of the array list. */
    public int size() {
        return size;
    }

    /** prints the list of size size. */
    public void printDeque() {
        if (size > 0) {
            for (int i = 0; i < items.length - 1; i++) {
                if (items[i] != null) {
                    System.out.print(items[i] + " ");
                }
            }
        }
        System.out.println();
    }

    /** @return the first item of the list and removes it from the list. */
    public T removeFirst() {
        if (size > 0) {
            T pop = items[nextFirst + 1];
            items[nextFirst + 1] = null;
            nextFirst++;
            size--;

            if (items.length > 15) {
                double usageFactor = (double) size / (double) items.length;
                if (usageFactor < 0.25) {
                    resizeDown();
                }
            }

            return pop;
        }
        return null;
    }

    /** @return the last item of the list and removes it from the list. */
    public T removeLast() {
        if (size > 0) {
            T pop = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast--;
            size--;

            if (items.length > 15) {
                double usageFactor = (double) size / (double) items.length;
                if (usageFactor < 0.25) {
                    resizeDown();
                }
            }

            return pop;
        }
        return null;
    }

    /** @return the item of the list at @param index . */
    public T get(int index) {
        if (index < size) {
            return items[nextFirst + 1 + index];
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> array = new ArrayDeque<>();

        array.addFirst(1);
        array.addFirst(2);
        array.addFirst(3);
        array.addFirst(4);
        array.addFirst(5);
        array.addLast(6);
        array.addLast(7);
        array.addLast(8);

        array.printDeque();
        System.out.println(array.nextFirst);
        System.out.println(array.nextLast);
    }

    /** returns a smaller array from index start to index end inclusive. */
    private T[] slice(T[] array, int start, int end) {
        T[] sliced = (T[]) new Object[end - start + 1];
        int index = 0;
        for (int i = start; i <= end; i++) {
            sliced[index] = array[i];
        }
        return sliced;
    }

    /** returns a single array of arr1 and arr2 merged together respectively and in order. */
    private T[] merge(T[] arr1, T[] arr2) {
        T[] sliced = (T[]) new Object[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            sliced[i] = arr1[i];
        }

        int count = 0;
        for (int j = arr1.length; j < sliced.length; j++) {
            sliced[j] = arr2[count];
            count++;
        }

        return sliced;
    }
}

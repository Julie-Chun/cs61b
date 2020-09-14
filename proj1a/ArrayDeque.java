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
        resize();
    }
    /** adds @param item to the end of the list. */
    public void addLast(T item) {
        items[nextLast] = item;
        size++;
        nextLast++;
        resize();
    }

    /** rescales the list so that its usage factor is more
     * than 25% and the nextFront and nextLast has a space in the list. */
    private void resize() {
        if (items.length > 15) {
            double usageFactor = (double) size / (double) items.length;
            if (usageFactor < 0.25) {
                T[] smaller = (T[]) new Object[size / 2];
                System.arraycopy(items, nextFirst, smaller, 0, size + 2);
                items = smaller;
                nextLast = size + 1;
                nextFirst = 0;
            }
        }
        if (nextFirst < 0 || nextLast >= items.length) {
            T[] larger = (T[]) new Object[size * 2];
            System.arraycopy(items, 0, larger, nextFirst + 2, items.length - 1);
            items = larger;
            nextFirst = (size / 2) - 1;
            nextLast = (size / 2) + size;
        }
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
            resize();
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
            resize();
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

}

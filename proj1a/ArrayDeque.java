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

        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }

        if (size >= items.length) {
            resizeUp();
        }

        if (items.length > 15) {
            double usageFactor = (double) size / (double) items.length;
            if (usageFactor < 0.25) {
                resizeDown();
            }
        }
    }
    /** adds @param item to the end of the list. */
    public void addLast(T item) {
        items[nextLast] = item;
        size++;
        if (nextLast == items.length) {
            nextLast = 0;
        } else {
            nextLast++;
        }

        if (size >= items.length) {
            resizeUp();
        }

        if (items.length > 15) {
            double usageFactor = (double) size / (double) items.length;
            if (usageFactor < 0.25) {
                resizeDown();
            }
        }
    }

    /** rescales the list so that its usage factor is more
     * than 25% and the nextFront and nextLast has a space in the list. */
    private void resizeUp() {
        int first = nextFirst + 1;

        if (nextFirst >= items.length) {
            first = 0;
        }

        T[] holder = (T[]) new Object[size * 2];
        System.arraycopy(items, first, holder, 0, items.length - first);

        items = holder;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** rescales the list so that its usage factor is more
     * than 25% and the nextFront and nextLast has a space in the list. */
    private void resizeDown() {
        int first = nextFirst + 1;

        T[] holder = (T[]) new Object[size / 2];

        if (nextLast != 0) {
            System.arraycopy(items, 0, holder, items.length - first, nextLast);
        }

        items = holder;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** @return true if the array list is empty. */
    public boolean isEmpty() {
        for (int i = 0; i < items.length; i++) {
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
        int first = nextFirst + 1;

        if (nextFirst >= items.length) {
            first = 0;
        }

        if (size > 0) {
            for (int i = first; i < items.length; i++) {
                if (items[i] != null) {
                    System.out.print(items[i] + " ");
                }
            }

            if (nextLast != 0) {
                for (int i = 0; i < first; i++) {
                    if (items[i] != null) {
                        System.out.print(items[i] + " ");
                    }
                }
            }
        }
        System.out.println();
    }

    /** @return the first item of the list and removes it from the list. */
    public T removeFirst() {
        if (size > 0) {
            int first = nextFirst + 1;

            if (nextFirst == items.length - 1) {
                first = 0;
            }

            T pop = items[first];
            items[first] = null;

            if (nextFirst == items.length) {
                nextFirst = 0;
            } else {
                nextFirst++;
            }

            size--;

            if (size >= items.length) {
                resizeUp();
            }

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
            int last = nextLast - 1;

            if (nextLast == 0) {
                last = items.length - 1;
            }

            T pop = items[last];
            items[last] = null;

            if (nextLast == 0) {
                nextLast = items.length;
            } else {
                nextLast--;
            }

            size--;

            if (size >= items.length) {
                resizeUp();
            }

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
            return items[(nextFirst + 1 + index) % items.length];
        }
        return null;
    }

}

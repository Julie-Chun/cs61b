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
        this.size = 0;
        this.items = (T[]) new Object[8];
        this.nextFirst = this.items.length / 2;
        this.nextLast = this.nextFirst + 1;
    }

    /** adds @param item to the front of the list. */
    public void addFirst(T item) {
        this.size++;
        if (this.size > this.items.length) {
            resizeUp();
        }

        this.items[this.nextFirst] = item;
        this.nextFirst -= 1;

        if (this.nextFirst < 0) {
            this.nextFirst = this.items.length - 1;
        }
    }

    /** adds @param item to the end of the list. */
    public void addLast(T item) {
        size++;

        if (size > items.length) {
            resizeUp();
        }
        items[nextLast] = item;
        nextLast++;

        if (nextLast == items.length) {
            nextLast = 0;
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
        int first = getFirstIndex();
        int last = getLastIndex();

        if (first < last) {
            T[] copy = (T[]) new Object[items.length * 2];
            System.arraycopy(items, 0, copy, 0, items.length);
            items = copy;
            nextFirst = items.length - 1;
            nextLast = size() - 1;
        } else {
            T[] front = slice(items, 0, last);
            T[] back = slice(items, first, items.length - 1);
            T[] copy = (T[]) new Object[items.length * 2];
            System.arraycopy(front, 0, copy, 0, front.length);
            System.arraycopy(back, 0, copy, copy.length - back.length, back.length);
            items = copy;
            nextFirst = items.length - back.length - 1;
            nextLast = front.length;
        }
    }

    /** rescales the list so that its usage factor is more than 25%. */
    private void resizeDown() {
        int first = getFirstIndex();
        int last = getLastIndex();

        if (first < last) {
            T[] holder = slice(items, first, last);
            T[] copy = (T[]) new Object[items.length / 2];
            System.arraycopy(holder, 0, copy, 0, holder.length);
            items = copy;
            nextFirst = items.length - 1;
            nextLast = size() - 1;
        } else if (first > last) {
            T[] front = slice(items, 0, last);
            T[] back = slice(items, first, items.length - 1);
            T[] copy = (T[]) new Object[items.length / 2];
            System.arraycopy(front, 0, copy, 0, front.length);
            System.arraycopy(back, 0, copy, copy.length - back.length, back.length);
            items = copy;
            nextFirst = items.length - back.length - 1;
            nextLast = front.length;
        }
    }
    /** @return true if the array list is empty. */
    public boolean isEmpty() {
        for (int i = 0; i < this.items.length; i++) {
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
        int first = getFirstIndex();

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
            int first = getFirstIndex();
            T pop = this.items[first];
            items[first] = null;
            nextFirst++;
            size--;

            if (nextFirst == items.length) {
                nextFirst = 0;
            }

            if (items.length > 15) {
                double usageFactor = (double) size / (double) items.length;
                if (usageFactor < 0.25) {
                    resizeDown();
                }
            }
            return pop;
        } else {
            return null;
        }
    }

    /** @return the last item of the list and removes it from the list. */
    public T removeLast() {
        if (size > 0) {
            int last = getLastIndex();
            T pop = this.items[last];
            items[last] = null;
            nextLast--;
            size--;

            if (nextLast < 0) {
                nextLast = items.length - 1;
            }

            if (items.length > 15) {
                double usageFactor = (double) size / (double) items.length;
                if (usageFactor < 0.25) {
                    resizeDown();
                }
            }
            return pop;
        } else {
            return null;
        }
    }

    /** @return the item of the list at @param index . */
    public T get(int index) {
        if (index < size) {
            int first = getFirstIndex();
            return items[(first + index) % items.length];
        } else {
            System.out.println("not inside index");
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> array = new ArrayDeque<>();

        //array.addFirst();
        //array.addLast();
        //array.removeFirst();
        //array.removeLast();
        //array.get();
        //array.isEmpty();


    }

    /** returns a smaller array from index start to index end inclusive. */
    private T[] slice(T[] array, int start, int end) {
        T[] sliced = (T[]) new Object[end - start + 1];
        int index = 0;
        for (int i = start; i <= end; i++) {
            sliced[index] = array[i];
            index++;
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

    /** returns the index where the first element of the deque starts. */
    private int getFirstIndex() {
        if (nextFirst == items.length - 1) {
            return 0;
        } else {
            return nextFirst + 1;
        }
    }

    /** returns the index where the last element of the deque starts. */
    private int getLastIndex() {
        if (nextLast == 0) {
            return items.length - 1;
        } else {
            return this.nextLast - 1;
        }
    }
}

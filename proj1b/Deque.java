/**
 * A Deque interface that creates any type of deque.
 * @author juliechun
 */

public interface Deque<T> {
    /** Adds an item to the front of the deque.
     * @param item is the item added to list.
     * */
    void addFirst(T item);

    /** Adds an item to the end of the list.
     * @param item is the item added to list.
     * */
    void addLast(T item);

    /** Checks if Deque is empty.
     * @return true if the list is empty.
     * */
    default boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    /** Returns the size of the deque. */
    int size();

    /** Prints all the items inside the deque. */
    void printDeque();

    /** Returns the first item of the deque of type T and
     * removes it from the deque. */
    T removeFirst();

    /** Returns the last item of the deque of type T and
     * removes it from the deque. */
    T removeLast();

    /**
     * @param index of the deque is searched to
     * @return items in the deque.
     */
    T get(int index);
}

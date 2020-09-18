/**
 * A Deque interface that creates any type of deque.
 * @author juliechun
 */

public interface Deque <T> {
    /** Adds an item to the front of the deque. */
    public void addFirst(T item);

    /** Adds an item to the end of the list
    public void addLast(T item);

    /** Checks if Deque is empty. */
    default public boolean isEmpty(){
        if (size() == 0){
            return true;
        }
        return false;
    }

    /** Returns the size of the deque. */
    public int size();

    /** Prints all the items inside the deque. */
    public void printDeque();

    /** Returns the first item of the deque of type T and
     * removes it from the deque. */
    public T removeFirst();

    /** Returns the last item of the deque of type T and
     * removes it from the deque. */
    public T removeLast();

    /**
     * @param index of the deque is searched to
     * @return items in the deque.
     */
    public T get(int index);
}

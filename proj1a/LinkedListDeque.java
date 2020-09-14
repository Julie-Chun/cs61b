/**
 * @author juliechun
 *  Creates a class of linked lists of TNodes.
 *  */

public class LinkedListDeque<T> {

    /** Creating list size. */
    private int size;

    /** Creating sentinel TNode. */
    private TNode sentinel;

    /** Creating first to reference first TNode in list. */
    private TNode first;

    /** Creating last to reference last TNode in list. */
    private TNode last;

    /** initiating all instance variables. */
    public LinkedListDeque() {
        sentinel = new TNode(null);
        sentinel.next = sentinel.prev = sentinel;
        first = sentinel.next;
        last = sentinel.prev;
        size = 0;
    }

    /** adds @param item to the front of the list after sentinel. */
    public void addFirst(T item) {
        TNode oldFirst = first;
        first = new TNode(item);
        first.prev = sentinel;
        sentinel.next = first;
        first.next = oldFirst;
        oldFirst.prev = first;

        if (size == 0) {
            last = first;
        }

        size++;
    }

    /** adds @param item to the back of the list before sentinel. */
    public void addLast(T item) {
        TNode oldLast = last;
        last = new TNode(item);
        last.next = sentinel;
        sentinel.prev = last;
        last.prev = oldLast;
        oldLast.next = last;

        if (size == 0) {
            first = last;
        }

        size++;
    }

    /** @returns true if list is empty, null is not. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** returns size of the list. */
    public int size() {
        return size;
    }

    /** prints all items in the list. */
    public void printDeque() {
        TNode current = first;

        while (current != sentinel) {
            System.out.print(current.value + " ");
            current = current.next;
        }

        System.out.println();
    }

    /** @return value of first node and removes it from the list. */
    public T removeFirst() {
        if (size != 0) {
            TNode oldFirst = first;
            first = oldFirst.next;
            first.prev = oldFirst.prev;
            oldFirst.prev.next = first;
            size--;
            return (T) oldFirst.value;
        }
        return null;
    }

    /** @return value of last node and removes it from the list. */
    public T removeLast() {
        if (size != 0) {
            TNode oldLast = last;
            last = oldLast.prev;
            last.next = oldLast.next;
            oldLast.next.prev = last;
            size--;
            return (T) oldLast.value;
        }

        return null;
    }

    /** @returns value of item at index @param index of the list. */
    public T get(int index) {
        TNode current = first;

        if (index < size) {
            while (index >= 0) {

                if (index == 0) {
                    return (T) current.value;
                }

                current = current.next;
                index--;
            }
        }

        return null;
    }

    /** @returns value of item at index @param index
     * of the list using recursion.
     * */
    public T getRecursive(int index) {
        T result = (T) first.value;

        if (index > size) {
            return null;
        } else if (index > 0) {
            first = first.next;
            result = getRecursive(index - 1);
            first = first.prev;
        }

        return result;
    }

    /** helper class that creates TNodes. */
    private static class TNode<T> {
        /** indicates the previous node. */
        private TNode prev;

        /** indicates the next node. */
        private TNode next;

        /** indicates the value of the item in node. */
        private T value;

        /** declares instance variables with
         * just @param val as item.
         * */
        private TNode(T val) {
            this(null, val, null);
        }

        /** an internal declaration of instance variables.
         * @param p for the previous node
         * @param n for the following node
         * @param val as the value of the item.
         * */
        private TNode(TNode p, T val, TNode n) {
            prev = p;
            value = val;
            next = n;
        }
    }
}



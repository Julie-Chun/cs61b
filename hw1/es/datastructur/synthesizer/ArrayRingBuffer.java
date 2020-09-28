package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

     /* Private class to implement Iterator. */
    private class BufferIterator implements Iterator<T> {
        private int pos;
        BufferIterator() {
            pos  = 0;
        }

        public boolean hasNext() {
            return pos < fillCount;
        }

        public T next() {
            T returnItem = rb[pos];
            pos++;
            return returnItem;
        }
    }

    /* Method to create iterator. */
    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    /* Method to get capacity. */
    @Override
    public int capacity() {
        return rb.length;
    }

    /* Method to get fillCount. */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Overrides the Object equals method.
     * returns true if the dequeue returns same value.
     */
    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer c = ((ArrayRingBuffer) o);
        if (this.rb.length == c.rb.length) {
            for (int i = 0; i < fillCount; i++) {
                if (rb[i] != c.rb[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        fillCount++;
        rb[last] = x;
        last++;

        if (last == capacity()) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        T out = rb[first];
        rb[first] = null;
        first++;
        fillCount--;

        if (first == capacity()) {
            first = 0;
        }

        return out;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

}

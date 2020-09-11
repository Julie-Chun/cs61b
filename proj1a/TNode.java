public class TNode<T> {
    public T item;
    public TNode next;
    public TNode prev;

    public TNode(T i) {
        item = i;
        next = null;
        prev = null;
    }
}

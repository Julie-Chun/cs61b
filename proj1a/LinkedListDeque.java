public class LinkedListDeque<T>{

    public int size;
    public TNode sentinel;
    public TNode first;
    public TNode last;

    public LinkedListDeque(){
        sentinel = new TNode (null);
        sentinel.next = sentinel.prev = sentinel;
        first = sentinel.next;
        last = sentinel.prev;
        size = 0;
    }

    public void addFirst(T item){
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

    public void addLast(T item){
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

    public boolean isEmpty(){
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        TNode current = first;

        while (current != sentinel) {
            System.out.print(current.value + " ");
            current = current.next;
        }

        System.out.println();
    }

    public T removeFirst(){
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

    public T removeLast(){
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

    // CHECK THE GETS
    public T get(int index){
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

    public T getRecursive(int index) {
        T result = (T) first.value;

        if (index > size) {
            return null;
        } else if (index > 0){
            first = first.next;
            result = getRecursive(index - 1);
            first = first.prev;
        }

        return result;
    }

    static class TNode<T>{
        public TNode prev;
        public TNode next;
        public T value;

        public TNode(T val){
            this(null,val,null);
        }

        public TNode(TNode p, T val, TNode n){
            prev = p;
            value = val;
            next = n;
        }
    }
}



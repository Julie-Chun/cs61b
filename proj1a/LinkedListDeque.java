public class LinkedListDeque<T>{
    public int size;
    public TNode first;
    public TNode last;

    public LinkedListDeque(){
        first.value = -111;
        last.value = -999;
        size = 0;

        /* Making circular sentinel topology*/
        first.next = first.prev = last;
        last.next = last.prev = first;
    }

    public void addFirst(T item){
        TNode oldFirst = first.next;
        first.next = new TNode(item);
        first.next.next = oldFirst;
        first.next.prev = first;
        oldFirst.prev = first.next;
        size++;
    }

    public void addLast(T item){
        TNode oldLast = last.prev;
        last.prev = new TNode(item);
        last.prev.next = last;
        last.prev.prev = oldLast;
        oldLast.next = last.prev;
        size--;
    }

    public boolean isEmpty(){
        if (first.next == last && last.prev == first) {
            return true;
        }
        return false;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        TNode current = first.next;
        while ()
    }

    public T removeFirst(){

        return null;
    }

    public T removeLast(){

        return null;
    }

    public T get(int index){

        return null;
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



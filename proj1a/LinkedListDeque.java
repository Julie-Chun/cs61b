public class LinkedListDeque<T>{
    public int size;
    public TNode first;
    public TNode last;

    public LinkedListDeque(){
        first.item = -111;
        last.item = -999;
        size = 0;
    }

    public void addFirst(T item){
        first.next = new TNode(item);
        size ++;
    }

    public void addLast(T item){

    }

    public boolean isEmpty(){

        return true;
    }

    public int size(){
        return size;
    }

    public void printDeque(){

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
}


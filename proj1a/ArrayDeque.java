public class ArrayDeque<T>{
    public int size;
    public T[] items;
    public int nextFirst;
    public int nextLast;

    public ArrayDeque(){
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = items.length / 2;
        nextLast = nextFirst + 1;
    }
    //System.arraycopy(items,0,holder,1,size);
    public void addFirst(T item){
        items[nextFirst] = item;
        size++;
        nextFirst--;
        resize();
    }

    public void addLast(T item){
        items[nextLast] = item;
        size++;
        nextLast++;
        resize();
    }

    public void resize(){
        if(items.length > 15){
            double usageFactor = (double)size/(double)items.length;
            if( usageFactor < 0.25){
                T[] smaller = (T[]) new Object[size + 2];
                System.arraycopy(items,nextFirst,smaller,0,size+2);
                items = smaller;
                nextLast = size + 1;
                nextFirst = 0;
            }
        }
        if (nextFirst < 0){
            T[] larger = (T[]) new Object[items.length + 1];
            System.arraycopy(items,0,larger,nextFirst + 2, items.length - 1);
            items = larger;
            nextFirst++;
            nextLast ++;
        }
        if (nextLast >= items.length) {
            T[] larger = (T[]) new Object[items.length + 1];
            System.arraycopy(items,0,larger,0,items.length);
            items = larger;
        }
    }

    public boolean isEmpty(){
        for (int i = 0; i < size; i++){
            if (items[i] != null){
                return false;
            }
        }
        return true;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        if (size > 0) {
            for (int i = 0; i < items.length - 1; i++) {
                if (items[i] != null) {
                    System.out.print(items[i] + " ");
                }
            }
        }
        System.out.println();
    }


    public T removeFirst(){
        if (size > 0) {
            T pop = items[nextFirst + 1];
            items[nextFirst + 1] = null;
            nextFirst++;
            size--;
            resize();
            return pop;
        }
        return null;
    }

    public T removeLast(){
        if (size > 0) {
            T pop = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast--;
            size--;
            resize();
            return pop;
        }
        return null;
    }

    public T get(int index){
        if (index < size){
            return items[index];
        }
        return null;
    }
}
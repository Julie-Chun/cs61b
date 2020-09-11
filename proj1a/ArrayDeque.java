public class ArrayDeque<T>{
    public int size;
    public T[] items;

    public ArrayDeque(){
        size = 0;
        items = (T[]) new Object[8];
    }

    public void addFirst(T item){
        if (items[0] != null){
            T[] holder = (T[]) new Object[size + 1];
            System.arraycopy(items,0,holder,1,size);
            holder[0] = item;
            items = holder;
        }else{
            items[0] = item;
        }

        size++;
    }

    public void addLast(T item){
        if (size == items.length){
            T[] holder =  (T[]) new Object[size + 1];
            System.arraycopy(items,0,holder,0,size);
            holder[size] = item;
            items = holder;
        }else{
            items[size + 1] = item;
        }

        size++;
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
            for (int i = 0; i < size; i++) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }

    public void resize(){
        if(items.length > 15){
            if((double)size/(double)items.length < 0.25){

            }
        }
    }

    public T removeFirst(){
        if (size > 0){
            T pop = items[0];
            items[0] = null;
            size--;
            System.arraycopy(items,0,items,0,size);
            return pop;
        }
        return null;
    }

    public T removeLast(){
        if(size > 0){
            T pop = items[size];
            items[size] = null;
            size--;
            System.arraycopy(items,0,items,0,size);
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
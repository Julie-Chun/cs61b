import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node head;
    private int size;

    /* create a bst node. */
    private class Node {
        private K key;
        private V val;
        private Node left, right;

        Node(K k, V v) {
            this.key = k;
            this.val = v;
        }
    }

    public BSTMap() {
        this.clear();
    }

    /* Prints contents of the BST from lowest to highest. */
    public void printInOrder() {
        printHelper(head);
    }

    private void printHelper(Node h) {
        if (h == null) {
            return;
        }
        printHelper(h.left);
        System.out.println("{key: " + h.key + ", value: " + h.val + "}");
        printHelper(h.right);
    }


    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(head, key);
    }

    private V getHelper(Node n, K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (n == null) {
            return null;
        }
        if (n.key.compareTo(key) > 0) {
            return getHelper(n.left, key);
        } else if (n.key.compareTo(key) < 0) {
            return getHelper(n.right, key);
        } else {
            return n.val;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            return;
        }
        head = putHelper(head, key, value);
    }

    private Node putHelper(Node h, K k, V v) {
        if (h == null) {
            size++;
            return new Node(k, v);
        }
        if (h.key.compareTo(k) < 0) {
            h.right = putHelper(h.right, k, v);
        }
        if (h.key.compareTo(k) > 0) {
            h.left = putHelper(h.left, k, v);
        }
        if (h.key.compareTo(k) == 0) {
            h.val = v;
        }
        return h;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}

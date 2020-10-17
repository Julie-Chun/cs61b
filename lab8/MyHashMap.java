import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initalSize;
    private HashSet<K> keySet;
    private double loadFactor;
    private ArrayList<Node>[] buckets;
    private int size;

    private class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    public MyHashMap() {
        size = 0;
        initalSize = 16;
        keySet = new HashSet<>();
        loadFactor = 0.75;
        buckets = (ArrayList<Node>[]) new ArrayList[initalSize];

    }

    public MyHashMap(int initialSize) {
        size = 0;
        this.initalSize = initialSize;
        keySet = new HashSet<>();
        loadFactor = 0.75;
        buckets = (ArrayList<Node>[]) new ArrayList[initalSize];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        size = 0;
        this.initalSize = initialSize;
        this.loadFactor = loadFactor;
        keySet = new HashSet<>();
        buckets = (ArrayList<Node>[]) new ArrayList[initalSize];
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        buckets = (ArrayList<Node>[]) new ArrayList[initalSize];
        keySet = new HashSet<>();
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return keySet().contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = modHash(key, buckets.length);
        ArrayList<Node> bucketNodes = buckets[index];
        if (bucketNodes != null) {
            for (Node n : bucketNodes) {
                K kk = n.key;
                if (n.key.equals(key)) {
                    return n.value;
                }
            }
        }
        return null;
    }

    /* gets the floorMod of the Hashcode of keys. */
    public int modHash(K k, int bucketL) {
        int num = Math.floorMod(k.hashCode(), bucketL);
        return num;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        int hashIndex = modHash(key, buckets.length);
        ArrayList<Node> bucketNodes = buckets[hashIndex];

        if (bucketNodes == null) {
            buckets[hashIndex] = new ArrayList<Node>();
            bucketNodes = buckets[hashIndex];
            bucketNodes.add(new Node(key, value));
            keySet.add(key);
            size++;
        } else if (keySet.contains(key)) {
            for (Node n : bucketNodes) {
                if (n.key.equals(key)) {
                    n.value = value;
                }
            }
        } else {
            bucketNodes.add(new Node(key, value));
            keySet.add(key);
            size++;
        }

        double currentLoadFactor = keySet.size() / buckets.length;
        if (currentLoadFactor > loadFactor) {
            resize();
        }

    }

    /* helper function that resizes buckets when loadingFactor is greater than 0.75. */
    private void resize() {
        ArrayList<Node>[] tempBucket = (ArrayList<Node>[]) new ArrayList[buckets.length * 2];
        for (K k : keySet) {
            int index = modHash(k, tempBucket.length);
            if (tempBucket[index] == null) {
                tempBucket[index] = new ArrayList<>();
            }
            tempBucket[index].add(new Node(k, get(k)));
        }

        this.buckets = tempBucket;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return this.keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /* Returns an Iterator that iterates over the stored keys. */
    @Override
    public Iterator iterator() {
        return keySet.iterator();
    }
}

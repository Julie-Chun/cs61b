package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /* Represents the min heap. */
    private Node[] heap;

    /* Keeps track of all items in heap. */
    private HashMap<T, Integer> items;

    /**
     * class constructor.
     * initializes Node array heap and HashMap items.
     */
    public ArrayHeapMinPQ() {
        heap = new ArrayHeapMinPQ.Node[10];
        items = new HashMap<>();
        heap[0] = null;
    }

    /* helper class of Node. */
    private class Node {
        T item;
        double priority;

        Node left, right;

        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
            left = null;
            right = null;
        }
    }

    /* helper class to get index of parent node in items set. */
    private int getParentIndex(Node child) {
        int index = items.get(child.item);
        return index / 2;
    }

    /* helper class to get index of left child node in items set. */
    private int getLeftIndex(Node parent) {
        int index = items.get(parent.item);
        return index * 2;
    }

    /* helper class to get index of right child node in items set. */
    private int getRightIndex(Node parent) {
        int index = items.get(parent.item);
        return (index * 2) + 1;
    }

    /**
     * Adds an item of type T with the given priority.
     * If the item already exists, throw an IllegalArgumentException.
     * You may assume that item is never null.
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        } else {
            Node node = new Node(item, priority);
            int index = items.size() + 1;
            items.put(item, index);
            heap[index] = node;

            // add to end of the heap and items set.
            Node parent = heap[getParentIndex(node)];

            // attach to a parent node if not a root.
            if (parent != null) {
                if (getLeftIndex(parent) == index) {
                    parent.left = node;
                } else if (getRightIndex(parent) == index) {
                    parent.right = node;
                }


                int leftIdx = getLeftIndex(node);
                int rightIdx = getRightIndex(node);

                // swim or sink added item according to priority.

                if (prtyLessThan(node, parent)) {
                    heap = swim(node, heap);
                } else if (rightIdx < heap.length) {
                    if (heap[rightIdx] != null && heap[leftIdx] != null) {
                        if (prtyLessThan(heap[leftIdx], node)
                                && prtyLessThan(heap[rightIdx], node)) {
                            heap = sink(node, heap);
                        }
                    } else if (heap[rightIdx] == null && heap[leftIdx] != null) {
                        if (prtyLessThan(heap[leftIdx], node)) {
                            heap = sink(node, heap);
                        }
                    } else if (heap[rightIdx] != null && heap[leftIdx] == null) {
                        if (prtyLessThan(heap[rightIdx], node)) {
                            heap = sink(node, heap);
                        }
                    }
                } else if (leftIdx < heap.length) {
                    if (heap[leftIdx] != null) {
                        heap = sink(node, heap);
                    }
                }
            }

            //resize up condition.
            if (items.size() == heap.length - 1) {
                resizeUp();
            }

            // resize down condition.
            if (((double) (heap.length - items.size() - 1) / (double) heap.length) > (3.0 / 4.0)) {
                resizeDown();
            }
        }
    }

    /* the swim up method to help reorganize heap after add. */
    private Node[] swim(Node item, Node[] hp) {
        if (getParentIndex(item) == 0) {
            return hp;
        }

        Node parent = hp[getParentIndex(item)];

        if (prtyLessThan(parent, item) || parent.priority == item.priority) {
            return hp;
        } else {
            swap(item, parent, hp);
            hp = swim(item, hp);
        }

        return hp;
    }

    /* the sink down method to help reorganize heap after remove. */
    private Node[] sink(Node item, Node[] hp) {
        if (item.left == null && item.right == null) {
            return hp;
        }

        Node left = item.left;
        Node right = item.right;

        if (left != null && right != null) {
            if (prtyLessThan(left, right)) {
                swap(left, item, hp);
            } else {
                swap(right, item, hp);
            }
            hp = sink(item, hp);
        } else if (left == null && right != null) {
            swap(right, item, hp);
            hp = sink(item, hp);
        } else if (left != null && right == null) {
            swap(left, item, hp);
            hp = sink(item, hp);
        } else {
            return hp;
        }

        return hp;
    }

    /* helper to both sink and swim of swapping two nodes in set and heap. */
    private void swap(Node lower, Node higher, Node[] hp) {
        Node parent = hp[getParentIndex(higher)];
        Node higherChild = null;
        int highIndex = items.get(higher.item);
        int lowIndex = items.get(lower.item);

        //parent of higher node switches child.
        if (parent != null) {
            if (parent.left == higher) {
                parent.left = lower;
            } else if (parent.right == higher) {
                parent.right = lower;
            }
        }

        //checks if lower node is the left or right child of higher node.
        if (getLeftIndex(higher) == lowIndex) {
            higherChild = higher.right;
            higher = switchChildren(higher, lower.left, lower.right);
            lower = switchChildren(lower, higher, higherChild);
        } else {
            higherChild = higher.left;
            higher = switchChildren(higher, lower.left, lower.right);
            lower = switchChildren(lower, higherChild, higher);
        }

        hp[lowIndex] = higher;
        hp[highIndex] = lower;
        items.replace(higher.item, lowIndex);
        items.replace(lower.item, highIndex);
    }

    /* helper to swap that replaces children of parent to left and right. */
    private Node switchChildren(Node parent, Node left, Node right) {
        parent.left = left;
        parent.right = right;
        return parent;
    }

    /* makes heap larger is filled up. */
    private void resizeUp() {
        Node[] temp = new ArrayHeapMinPQ.Node[2 * heap.length];
        System.arraycopy(heap, 0, temp, 0, items.size() + 1);
        heap = temp;
    }

    /* shrinks heap is 3/4 of heao is empty. */
    private void resizeDown() {
        Node[] temp = new ArrayHeapMinPQ.Node[heap.length / 2];
        System.arraycopy(heap, 0, temp, 0, items.size() + 1);
        heap = temp;
    }

    /* checks if priority of n1 is less than that of n2. */
    private boolean prtyLessThan(Node n1, Node n2) {
        return n1.priority < n2.priority;
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    /**
     * Returns the item with smallest priority.
     * If no items exist, throw a NoSuchElementException.
     */
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {
            return heap[1].item;
        }
    }

    /**
     * Removes and returns the item with smallest priority.
     * If no items exist, throw a NoSuchElementException.
     */
    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {
            T item = heap[1].item;
            int index = items.get(item);
            Node left = heap[1].left;
            Node right = heap[1].right;

            Node parent = heap[getParentIndex(heap[items.size()])];

            if (parent != null) {
                if (getLeftIndex(parent) == items.size()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }

            items.remove(item);

            heap[1] = heap[items.size() + 1];
            heap[items.size() + 1] = null;

            Node head = heap[1];

            if (left != head) {
                head.left = left;
            }

            if (right != head) {
                head.right = right;
            }

            if (items.size() > 0) {
                items.replace(heap[1].item, index);

                if (head.left != null && head.right != null) {
                    if (prtyLessThan(head.left, head) && prtyLessThan(head.right, head)) {
                        heap = sink(head, heap);
                    }
                } else if (head.left == null && head.right != null) {
                    if (prtyLessThan(head.right, head)) {
                        heap = sink(head, heap);
                    }
                } else if (head.left != null && head.right == null) {
                    if (prtyLessThan(head.left, head)) {
                        heap = sink(head, heap);
                    }
                }
            }

            // resize down condition.
            if (((double) (heap.length - items.size() - 1) / (double) heap.length) > (3.0 / 4.0)) {
                resizeDown();
            }

            return item;
        }
    }

    /* Returns the number of items. */
    @Override
    public int size() {
        return items.size();
    }

    /**
     * Sets the priority of the given item to the given value.
     * If the item does not exist, throw a NoSuchElementException.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        } else {
            int index = items.get(item);
            Node n = heap[index];
            n.priority = priority;

            Node parent = heap[getParentIndex(n)];
            Node right = heap[getRightIndex(n)];
            Node left = heap[getLeftIndex(n)];

            if (parent != null && prtyLessThan(n, parent)) {
                heap = swim(n, heap);
            }

            int rightIdx = getRightIndex(n);
            int leftIdx = getLeftIndex(n);
            if (rightIdx < heap.length) {
                if (right != null && left != null) {
                    if (prtyLessThan(left, n) && prtyLessThan(right, n)) {
                        heap = sink(n, heap);
                    }
                } else if (right == null && left != null) {
                    if (prtyLessThan(left, n)) {
                        heap = sink(n, heap);
                    }
                } else if (right != null && left == null) {
                    if (prtyLessThan(right, n)) {
                        heap = sink(n, heap);
                    }
                }
            } else if (leftIdx < heap.length) {
                if (left != null) {
                    heap = sink(n, heap);
                }
            }
        }
    }

    /* for testing the position of Nodes in tree. */
    protected HashMap getItems() {
        return this.items;
    }

    protected Node[] getHeap() {
        return this.heap;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> items = new HashMap<>();
        items.put("a", 0);
        items.put("b", 1);
        items.put("c", 2);
        items.replace("b", 6);
        System.out.println(items.keySet());
        System.out.println(items.get("b"));
    }
}

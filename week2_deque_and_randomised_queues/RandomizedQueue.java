import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int tail;
    private int arraySize;


    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        tail = 0;
        arraySize = 1;
    }

    private Item[] newGenericArray(int size) {
        return (Item[]) new Object[size];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        validateItem(item);
        autoGrowArray();
        q[tail++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        assertNotEmpty();
        int randomIndex = getRandomIndex(tail);
        Item item = q[randomIndex];
        tail--;
        q[randomIndex] = q[tail];
        q[tail] = null;

        autoShrinkArray();
        return item;
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void assertNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private void autoGrowArray() {
        if (tail == arraySize) {
            arraySize = arraySize * 2;
            q = resizeArray(arraySize);
        }
    }

    private void autoShrinkArray() {
        if (arraySize > 2 && tail == arraySize / 4) {
            arraySize = arraySize / 2;
            q = resizeArray(arraySize);
        }
    }

    private Item[] resizeArray(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        return copyItems(newArray);
    }

    private Item[] copyItems(Item[] newArray) {
        for (int i = 0; i < tail; i++) {
            newArray[i] = q[i];
        }
        return newArray;
    }

    private int getRandomIndex(int max) {
        return StdRandom.uniform(max);
    }

    public Item sample() {
        assertNotEmpty();
        return q[getRandomIndex(tail)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        Item[] qCopy = copyArray();
        int tailCopy = tail;

        private Item[] copyArray() {
            Item[] newArray = newGenericArray(arraySize);
            for (int i = 0; i < tail; i++) {
                newArray[i] = q[i];
            }
            return newArray;
        }

        public boolean hasNext() {
            return tailCopy != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int i = getRandomIndex(tailCopy--);

            Item item = qCopy[i];
            if (i != tail) qCopy[i] = qCopy[tailCopy];
            qCopy[tailCopy] = null;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<Integer>();
        rQueue.enqueue(1);
        rQueue.enqueue(2);
        rQueue.enqueue(3);
        rQueue.enqueue(4);
        rQueue.enqueue(5);

        for (int item : rQueue) {
            System.out.println(item);
        }
    }

}

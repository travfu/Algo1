import java.util.Iterator;
import java.util.NoSuchElementException;

// Implement Deque using a bi-directional linked list

public class Deque<Item> implements Iterable<Item> {
    private Node first; // pointer to head of linked list
    private Node last;  // pointer to tail of linked list
    private int size;   // number of items in linked list

    public Deque() {
        size = 0;
    }

    private class Node {
        public Item item;
        public Node next;
        public Node prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        validateItem(item);
        Node node = new Node();
        node.item = item;

        if (size > 0) {
            node.next = first;  // connect to forwards linked list
            first.prev = node;  // connect to backwards linked list
        }
        else {
            last = node;
        }
        first = node;

        size++;
    }

    public void addLast(Item item) {
        validateItem(item);
        Node node = new Node();
        node.item = item;

        if (size > 0) {
            node.prev = last;  // connect to backwards linked list
            last.next = node;  // connect to forwards linked list
        }
        else {
            first = node;
        }
        last = node;

        size++;
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    public Item removeFirst() {
        assertNotEmpty();
        Item item = first.item;
        if (size > 1) {
            first = first.next;
            first.prev = null;
        }
        else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        assertNotEmpty();
        Item item = last.item;
        if (size > 1) {
            last = last.prev;
            last.next = null;
        }
        else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    private void assertNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Iterator<Item> iterator() {
        return new ForwardListIterator();
    }

    private class ForwardListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(String.format("Is deque empty? %s", deque.isEmpty()));

        System.out.println("inserting items into deque...");
        deque.addLast(11);
        deque.addLast(22);
        deque.addLast(33);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);

        System.out.println(String.format("Is deque empty? %s", deque.isEmpty()));
        System.out.println(String.format("size: %s", deque.size()));

        int first = deque.removeFirst();
        int last = deque.removeLast();
        System.out.println(String.format("removeFirst: %s", first));
        System.out.println(String.format("removeLast: %s", last));

        System.out.println(String.format("size: %s", deque.size()));
        for (int x : deque) {
            System.out.println(x);
        }
    }

}

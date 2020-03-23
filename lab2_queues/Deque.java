/*
Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports
adding and removing items from either the front or the back of the data structure.

巩固：n++ 是先执行n++再进行赋值返回的只却是n；++n 是先赋值之后再执行++n
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int n;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // inner class: Node
    private class Node {
        Item item;
        Node next;
        Node pre;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.pre = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.pre = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        n++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        n--;
        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.pre;
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // inner class: an iterator over items in order from front to back
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        private int i = n;

        @Override
        public boolean hasNext() {
            //return current != null;
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            i--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        System.out.println("Beginning: deque is empty: " + deque.isEmpty());
        deque.addFirst("name");
        deque.addFirst("My");
        deque.addLast("is");
        deque.addLast("HYL");
        Iterator<String> i = deque.iterator();
        while (i.hasNext()) {
            String s = i.next();
            System.out.print(s + " ");
        }
        System.out.println("\nSize of the deque: " + deque.size());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println("Finishing: deque is empty: " + deque.isEmpty());
    }
}


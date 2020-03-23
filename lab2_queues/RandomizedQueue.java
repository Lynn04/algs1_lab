/*
 * Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly
 * at random among items in the data structure.
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rQueue;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        rQueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == rQueue.length) {
            resize(2 * rQueue.length);
        }
        rQueue[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int index = StdRandom.uniform(n);
        Item swap = rQueue[index];
        rQueue[index] = rQueue[n - 1];
        rQueue[n - 1] = null;
        n--;
        return swap;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = rQueue[i];
        }
        rQueue = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int index = StdRandom.uniform(n);
        return rQueue[index];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = n;
        private Item[] iteCopy = (Item[]) new Object[i];

        private ArrayIterator() {
            for (int m = 0; m < i; m++) {
                iteCopy[m] = rQueue[m];
            }
        }

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            int index = StdRandom.uniform(i);
            Item swap = iteCopy[index];
            iteCopy[index] = iteCopy[i - 1];
            iteCopy[i - 1] = null;
            i--;
            return swap;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        System.out.println("Is empty? " + rq.isEmpty());
        rq.enqueue("I");
        rq.enqueue("am");
        rq.enqueue("Lynn");
        System.out.println("Is empty? " + rq.isEmpty());
        System.out.println("Size: " + rq.size());
        System.out.println("Sample: " + rq.sample());
        Iterator<String> i = rq.iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        System.out.println("Is empty? " + rq.isEmpty());
    }

}

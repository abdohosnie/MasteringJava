package datastructures;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A high-performance LIFO Stack implementation using a resizing array.
 * Includes a LIFO Iterator to traverse from Top to Bottom.
 */
@SuppressWarnings("unchecked")
public class ArrayStack<T> implements Iterable<T> {

    private T[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayStack() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void push(T item) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    public T pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        // Get the top item
        T item = elements[--size];

        // CRITICAL: Prevent memory leak (loitering object)
        elements[size] = null;

        return item;
    }

    public T peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = elements.length * 2;
        // Arrays.copyOf is an optimized native method for array resizing
        elements = Arrays.copyOf(elements, newCapacity);
    }

    // --- LIFO Iterator ---
    // Allows: for (T item : stack) -> prints Top item first
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            // Start at the top (size - 1)
            private int currentIndex = size - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return elements[currentIndex--];
            }
        };
    }
}
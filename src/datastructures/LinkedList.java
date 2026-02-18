package datastructures;

import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings("ConstantConditions")
public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Static inner Node class.
     */
    private static class Node<T> {
        private final T data;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }
    }

    /* ==============================
       Core Operations
       ============================== */
    public void addFirst(T element) {
        Objects.requireNonNull(element);

        Node<T> newNode = new Node<>(element);
        newNode.next = head;
        head = newNode;

        if(size == 0) {
            tail = newNode;
        }
        size++;
    }

    public void addLast(T element) {
        Objects.requireNonNull(element, "element cannot be null");

        Node<T> newNode = new Node<>(element);

        if(size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        T data = head.data;
        head = head.next;
        size--;

        if (size == 0) {
            tail = null;
        }

        return data;
    }

    public T removeLast() {
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        if(size == 1) {
            return removeFirst();
        }

        Node<T> current = head;

        // Traverse to node before tail
        while(current.next != null) {
            current = current.next;
        }

        T data = tail.data;
        current.next = null;
        tail = current;
        size--;

        return data;
    }

    public T get(int index) {
        validateIndex(index);

        Node<T> current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /* ==============================
       Private Helpers
       ============================== */
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}

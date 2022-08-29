package com.solvd.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class CustomLinkedList<T> implements Iterable<T> {
    private static final Logger log = LogManager.getLogger(CustomLinkedList.class);
    private Node<T> head, tail;

    class Node<T> {
        private T object;
        private Node<T> next;

        public Node(T object, Node<T> next) {
            this.object = object;
            this.next = next;
        }

        //Setters and getters
        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public void insert(T object) {
        Node<T> newNode = new Node(object, null);
        if (this.head == null) {
            tail = head = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public Node<T> getHead() {
        return head;
    }

    // return Tail
    public Node<T> getTail() {
        return tail;
    }

    public Iterator<T> iterator() {
        return new ListIterator<>(this);
    }

    public void remove(T key) {
        Node<T> currentNode = this.head;
        Node<T> previousNode = null;

        if (currentNode != null && currentNode.object == key) {
            this.head = currentNode.next;
            if (currentNode == this.tail) {
                this.tail = currentNode.next;
            }
        }

        while (currentNode != null && currentNode.object != key) {
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        if (currentNode != null) {
            log.info("Node found and removed");
            if (currentNode == this.tail) {
                this.tail = previousNode;
            }
            previousNode.next = currentNode.next;

        }

        if (currentNode == null) {
            log.error("Node not found");
        }
    }

    public class ListIterator<T> implements Iterator<T> {
        CustomLinkedList<T>.Node<T> current;

        // initialize pointer to head of the list for iteration
        public ListIterator(CustomLinkedList<T> list) {
            current = list.getHead();
        }

        // returns false if next element does not exist
        public boolean hasNext() {
            return current != null;
        }

        // return current data and update pointer
        public T next() {
            T data = current.getObject();
            current = current.getNext();
            return data;
        }
    }
}



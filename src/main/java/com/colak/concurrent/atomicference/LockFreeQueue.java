package com.colak.concurrent.atomicference;


import java.util.concurrent.atomic.AtomicReference;

class LockFreeQueue<T> {
    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;

    public static void main() {
        LockFreeQueue<Integer> queue = new LockFreeQueue<>();

        // Enqueue some items
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // Dequeue and print items
        System.out.println(queue.dequeue()); // Output: 1
        System.out.println(queue.dequeue()); // Output: 2
        System.out.println(queue.dequeue()); // Output: 3
        System.out.println(queue.dequeue()); // Output: null (queue is empty)
    }

    private static class Node<T> {
        final T value;
        final AtomicReference<Node<T>> next;

        Node(T value) {
            this.value = value;
            this.next = new AtomicReference<>(null);
        }
    }

    public LockFreeQueue() {
        // Create a dummy node to simplify enqueue and dequeue operations
        Node<T> dummy = new Node<>(null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);
        while (true) {
            Node<T> currentTail = tail.get();
            Node<T> tailNext = currentTail.next.get();
            if (currentTail == tail.get()) { // Ensure tail has not moved
                if (tailNext == null) { // Tail is at the actual end
                    if (currentTail.next.compareAndSet(null, newNode)) {
                        // Successfully added the new node, update the tail
                        tail.compareAndSet(currentTail, newNode);
                        return;
                    }
                } else {
                    // Tail was not at the end, move the tail forward
                    tail.compareAndSet(currentTail, tailNext);
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> headNext = currentHead.next.get();
            if (currentHead == head.get()) { // Ensure head has not moved
                if (currentHead == currentTail) { // Queue is empty or tail is catching up
                    if (headNext == null) {
                        return null; // Queue is empty
                    }
                    // Tail is lagging, move it forward
                    tail.compareAndSet(currentTail, headNext);
                } else {
                    // Retrieve value and move head forward
                    T value = headNext.value;
                    if (head.compareAndSet(currentHead, headNext)) {
                        return value;
                    }
                }
            }
        }
    }


}

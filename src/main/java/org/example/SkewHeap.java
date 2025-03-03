package org.example;

class SkewHeap<T extends Comparable<T>> {
    private static class Node<T> {
        T value;
        Node<T> left, right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root;

    public SkewHeap() {
        this.root = null;
    }

    private Node<T> merge(Node<T> h1, Node<T> h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        if (h1.value.compareTo(h2.value) > 0) {
            Node<T> temp = h1;
            h1 = h2;
            h2 = temp;
        }

        h1.right = merge(h1.right, h2);
        Node<T> temp = h1.left;
        h1.left = h1.right;
        h1.right = temp;

        return h1;
    }

    public void insert(T value) {
        root = merge(root, new Node<>(value));
    }

    public T extractMin() {
        if (root == null) throw new IllegalStateException("Heap is empty");
        T minValue = root.value;
        root = merge(root.left, root.right);
        return minValue;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void mergeWith(SkewHeap<T> other) {
        this.root = merge(this.root, other.root);
    }
}

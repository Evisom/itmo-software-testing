package org.example;
class SkewHeap {
    private static class Node {
       int value;
        Node left, right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public SkewHeap() {
        this.root = null;
    }

    private Node merge(Node h1, Node h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        if (h1.value> h2.value) {
            Node temp = h1;
            h1 = h2;
            h2 = temp;
        }

        h1.right = merge(h1.right, h2);
        Node temp = h1.left;
        h1.left = h1.right;
        h1.right = temp;

        return h1;
    }

    public void insert(int value) {
        root = merge(root, new Node(value));
    }

    public int extractMin() {
        if (root == null) throw new IllegalStateException("Heap is empty");
        int minValue = root.value;
        root = merge(root.left, root.right);
        return minValue;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void mergeWith(SkewHeap other) {
        this.root = merge(this.root, other.root);
    }
}

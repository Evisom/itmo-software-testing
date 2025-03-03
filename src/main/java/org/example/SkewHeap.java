package org.example;

class SkewHeap {

    int key;
    SkewHeap right;
    SkewHeap left;

    SkewHeap() {
        key = 0;
        right = null;
        left = null;
    }


    SkewHeap merge(SkewHeap h1, SkewHeap h2) {

        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;


        if (h1.key > h2.key) {
            SkewHeap temp = h1;
            h1 = h2;
            h2 = temp;
        }

        SkewHeap temp = h1.left;
        h1.left = h1.right;
        h1.right = temp;


        h1.left = merge(h2, h1.left);

        return h1;
    }

    SkewHeap construct(SkewHeap root, int[] heap, int n) {
        SkewHeap temp;
        for (int i = 0; i < n; i++) {
            temp = new SkewHeap();
            temp.key = heap[i];
            root = merge(root, temp);
        }
        return root;
    }

    void inorder(SkewHeap root) {
        if (root == null)
            return;
        else {
            inorder(root.left);
            System.out.print(root.key + "  ");
            inorder(root.right);
        }
        return;
    }
}

package homeworks.hw3;

class HashMap {
    class Entity {
        int key;
        int value;
    }

    class Basket {
        Node head;

        class Node {
            Entity entity;
            Node next;
        }

        public Integer find(int key) { // O(1)
            Node node = head;
            while (node != null) {
                if (node.entity.key == key) {
                    return node.entity.value;
                }
                node = node.next;
            }
            return null;
        }

        public boolean push(Entity entity) { // O(1)
            Node node = new Node();
            node.entity = entity;

            if (head == null) {
                head = node;
            } else {
                if (head.entity.key == entity.key) {
                    return false;
                } else {
                    Node cur = head;
                    while (cur.next != null) {
                        if (cur.next.entity.key == entity.key) {
                            return false;
                        }
                        cur = cur.next;
                    }
                    cur.next = node;
                }
            }
            return true;
        }

        public boolean remove(int key) { // O(1)
            if (head != null) {
                if (head.entity.key == key) {
                    head = head.next;
                    return true;
                } else {
                    Node cur = head;
                    while (cur.next != null) {
                        if (cur.next.entity.key == key) {
                            cur.next = cur.next.next;
                            return true;
                        }
                        cur = cur.next;
                    }
                }
            }
            return false;
        }
    }

    static final int INIT_SIZE = 16;

    Basket[] baskets;

    public HashMap() {
        this(INIT_SIZE);
    }

    public HashMap(int size) {
        baskets = new Basket[size];
    }

    private int getIndex(int key) {
        return key % baskets.length;
    }

    public Integer find(int key) { // O(1)
        int index = getIndex(key);
        Basket basket = baskets[index];
        if (basket != null) {
            return basket.find(key);
        }
        return null;
    }

    public boolean push(int key, int value) {
        int index = getIndex(key);
        Basket basket = baskets[index];
        if (basket == null) {
            basket = new Basket();
            baskets[index] = basket;
        }
        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;
        return basket.push(entity);
    }

    public boolean remove(int key) {
        int index = getIndex(key);
        Basket basket = baskets[index];
        if (basket != null) {
            return basket.remove(key);
        }
        return false;
    }
}

class Tree {
    Node root;

    class Node {
        int value;
        Node left;
        Node right;
        boolean color; 
    }

    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (node.value == value) {
            return node;
        }
        if (node.value < value) {
            return find(node.right, value);
        } else {
            return find(node.left, value);
        }
    }

    public void insert(int value) { 
        if (root == null) {
            root = new Node();
            root.value = value;
            root.color = false; // Корень всегда черный
        } else {
            root = insert(root, value);
            root.color = false; // Убедимся, что корень остается черным
        }
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            Node newNode = new Node();
            newNode.value = value;
            newNode.color = true; // Новый узел всегда красный
            return newNode;
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }

        // Балансировка дерева после вставки нового узла
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private boolean isRed(Node node) {
        return node != null && node.color;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.color = node.color;
        node.color = true;
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        newRoot.color = node.color;
        node.color = true;
        return newRoot;
    }

    private void flipColors(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }
}

public class Main {
    public static void main(String[] args) {
        Tree tree = new Tree();

        for (int i = 1; i <= 5; i++) {
            tree.insert(i);
        }

        // Проверяем результаты вставки и балансировки
        System.out.println(tree.find(3)); // Выводит: Node@<hashcode>
        System.out.println(tree.find(2)); // Выводит: Node@<hashcode>
    }
}
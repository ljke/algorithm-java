package skiplist;

import java.util.Random;

/**
 * Implement of skip list, skip list is a effective dynamic data structure
 * time complex of search, insert and delete: O(log n)
 * space complex: O(n)
 * scenario: SortedSet of Redis
 */
public class SkipList {
    private static final int MAX_LEVEL = 16; // level height of skip list, including origin list and index layer
    private int levelCount = 1; //current used level(skip list is dynamic update)
    private Node head = new Node(); //list with dummy head
    private Random r = new Random(); //used for keep balance

    // search method
    public Node find(int value) {
        Node p = head; // p point to a node in origin list
        for (int i = levelCount - 1; i >= 0; --i) { // search in every level, the key point is that [p] can inherit position of upper layer
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }
        if (p.forwards[0] != null && p.forwards[0].data == value) { //forwards[0] is origin list
            return p.forwards[0];
        } else {
            return null;
        }
    }

    public void insert(int value) {
        int level = randomLevel(); //random level that insert into, [n] means insert the bottom [n] layer
        // init node
        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;
        Node update[] = new Node[level]; //the insert position
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }
        //search insert position and replace [update]
        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }
        //insert operation
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }
        //update current used level
        if (levelCount < level) {
            levelCount = level;
        }
    }

    public void delete(int value) {
        Node[] update = new Node[levelCount];
        //search insert position and replace [update]
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }
        //delete operation
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; --i) {
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) { // only delete equal element
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }
    }

    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (r.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

    public void printAll(){
        Node p = head;
        while (p.forwards[0] != null) {
            System.out.println(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    public void init() {
        int item;
        for (int i = 0; i < 100; i++) {
            item = r.nextInt();
            System.out.println("Insert:" + item);
            insert(item);
        }
        printAll();
    }

    /**
     * Node store non-negative integer, not repeating
     */
    private class Node {
        private int data = -1;
        // the linked list in skip list has an array of next-nodes, not a single next-node as usual
        private Node forwards[] = new Node[MAX_LEVEL]; //each element represent a forward node in a layer
        private int maxLevel = 0;

        @Override
        public String toString() {

            return "{ data: " +
                    data +
                    "; level: " +
                    maxLevel +
                    " }";
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.init();
        skipList.insert(30);
        System.out.println(skipList.find(30));
        skipList.insert(40);
        skipList.delete(30);
        skipList.delete(40);
        skipList.printAll();
    }
}

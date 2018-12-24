package tree;

import java.util.*;

public class BinarySearchTree {
    private Node tree; //root node

    /**
     * find the first node that equals specify data, no include duplicate data
     *
     * @param data data that want to find
     * @return corresponding node if found, null if not found
     */
    public Node find(int data) {
        Node cur = tree;
        while (cur != null) {
            if (cur.data > data) {
                cur = cur.left;
            } else if (cur.data < data) {
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return null;
    }

    /**
     * find all nodes that equal specify data, include all duplicate data
     *
     * @param data data that want to find
     * @return corresponding list of nodes if found, empty list if not found
     */
    public List<Node> findAll(int data) {
        Node cur = tree;
        List<Node> res = new ArrayList<>();
        while (cur != null) { // look down until leaf node, equal does not stop
            if (cur.data > data) {
                cur = cur.left;
            } else { // equal or larger situation
                if (cur.data == data) {
                    res.add(cur);
                }
                cur = cur.right;
            }
        }
        return res;
    }

    /**
     * insert new node into tree, equal data will be treat as larger one
     *
     * @param data data that want to insert
     */
    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }
        Node cur = tree;
        while (cur != null) {
            if (cur.data > data) {
                if (cur.left == null) {
                    cur.left = new Node(data);
                    return;
                }
                cur = cur.left;
            } else { // equal or larger situation
                if (cur.right == null) {
                    cur.right = new Node(data);
                    return;
                }
                cur = cur.right;
            }
        }
    }

    /**
     * delete single node in a tree
     * in order to delete equal node, just repetitively call this function
     *
     * @param data data that want to delete
     * @return true if succeed, false if failed
     */
    public boolean delete(int data) {
        Node cur = tree;
        Node parent = null; // delete node need parent node
        // First, find suitable location
        while (cur != null && cur.data != data) {
            if (cur.data > data) {
                parent = cur;
                cur = cur.left;
            } else {
                parent = cur;
                cur = cur.right;
            }
        }
        //if no found, return
        if (cur == null) {
            return false;
        }
        //Second, divide into three different case, delete node
        //1. have two subtree
        if (cur.left != null && cur.right != null) {
            //find the min child in right subtree
            Node minP = cur.right;
            Node minPP = cur;
            while (minP.left != null) { // different from above, find larger or smaller just judge subtree node
                minPP = minP;
                minP = minP.left;
            }
            cur.data = minP.data;
            // tip: merge into subsequent operation to delete min child
            cur = minP;
            parent = minPP;
        }
        //2. have only one subtree
        Node child;
        if (cur.left != null) {
            child = cur.left;
        } else if (cur.right != null) {
            child = cur.right;
        } else {
            child = null; //3. have no subtree
        }

        if (parent == null) { //special situation: delete root node
            tree = child;
        } else if (parent.left == cur) { // cur is parent's left subtree
            parent.left = child;
        } else if (parent.right == cur) { // cur is parent's right subtree
            parent.right = child;
        } else {
            return false;
        }
        return true;
    }

    public Node findMax(Node cur) {
        if (cur == null) {
            return null;
        }
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur;
    }

    public Node findMin(Node cur) {
        if (cur == null) {
            return null;
        }
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    /**
     * In-order traverse to print sorted list
     *
     * @param cur the start node
     */
    public void inOrderSort(Node cur) {
        // tree is empty, return directly
        if (tree == null) {
            System.out.println("Tree is empty");
            return;
        }
        // Recursive implementation
        if (cur == null) {
            return;
        }
        inOrderSort(cur.left);
        System.out.printf("%d\t", cur.data);
        inOrderSort(cur.right);
    }

    /**
     * Level-order traverse to observe tree structure
     * The key point is to add empty node and divide line
     * -1 means empty position
     */
    public void levelOrderPrint() {
        if (tree == null) {
            System.out.println("Tree is empty");
            return;
        }
        Queue<Node> queue = new LinkedList<>(); // a queue that support breadth-first traversal
        ArrayList<Integer> curLine = new ArrayList<>(); // store current level
        Node empty = new Node(-1); //empty node that represent empty position
        Node cur;
        int count = 0; // count current level's node
        int boundary = 1; // the total node number in current level
        boolean emptyLine;
        queue.offer(tree);
        System.out.println("levelOrderPrint Start");
        while (true) {
            if (count == boundary) {
                //judge if current level is all empty
                emptyLine = true;
                for (int i : curLine) {
                    if (i >= 0) {
                        emptyLine = false;
                    }
                }
                if (emptyLine) { // if empty level, exit loop
                    break;
                } else {
                    System.out.println("--------------");
                    System.out.println(curLine);
                }
                boundary = boundary * 2; // Double the number of nodes per layer
                count = 0;
                curLine.clear();
            }
            cur = queue.poll();
            if (cur != null) {
                curLine.add(cur.data);
                count++;
                //empty node should also be enqueued to keep tree structure
                if (cur.left != null) {
                    queue.offer(cur.left);
                } else {
                    queue.offer(empty);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                } else {
                    queue.offer(empty);
                }
            } else { // this case should never occur
                break;
            }
        }
        System.out.println("levelOrderPrint Finish");
    }

    //another scheme, use last pointer to mark last position in every level
//    /**
//     * Level-order traverse to observe tree structure
//     */
//    public void levelOrderPrint() {
//        if (tree == null) {
//            System.out.println("Tree is empty");
//            return;
//        }
//        ArrayList<Integer> curLine = new ArrayList<>();
//        Queue<Node> queue = new LinkedList<>();
//        queue.offer(tree);
//        Node cur, last = tree, nLast = null;
//        while (!queue.isEmpty()) {
//            cur = queue.poll();
//            if (cur != null) {
//                curLine.add(cur.data);
//                if (cur.left != null) {
//                    queue.offer(cur.left);
//                    nLast = cur.left;
//                }
//                if (cur.right != null) {
//                    queue.offer(cur.right);
//                    nLast = cur.right;
//                }
//            } else {
//                curLine.add(null);
//            }
//
//            if (cur == last) {//节点等于last,说明到达了当前行的最后一个节点,
//                last = nLast;
//                curLine = new ArrayList<>();
//            }
//        }
//    }

    private class Node {
        private int data;
        private Node left = null;
        private Node right = null;

        Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        Random random = new Random();
        int num;
        for (int i = 0; i < 20; i++) {
            num = random.nextInt(20);
            System.out.println("Add new node:" + num);
            bst.insert(num);
            bst.levelOrderPrint();
            System.out.println("inOrderSort:");
            bst.inOrderSort(bst.tree);
            System.out.println();
        }
        bst.insert(10);
        bst.insert(10);
        Node node = bst.find(10);
        System.out.println("Find node:" + node.data);
        List<Node> nodes = bst.findAll(10);
        System.out.println("Find node number:" + nodes.size());
        System.out.println("Find max:" + bst.findMax(bst.tree).data);
        System.out.println("Find min:" + bst.findMin(bst.tree).data);
        boolean succeed = bst.delete(10);
        if (succeed) {
            System.out.println("Succeed");
            bst.levelOrderPrint();
            bst.inOrderSort(bst.tree);
        } else {
            System.out.println("Failed");
        }
        succeed = bst.delete(10);
        if (succeed) {
            System.out.println("Succeed");
            bst.levelOrderPrint();
            bst.inOrderSort(bst.tree);
        } else {
            System.out.println("Failed");
        }
    }
}

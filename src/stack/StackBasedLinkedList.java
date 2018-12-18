package stack;

public class StackBasedLinkedList {

    private Node top = null;

    public void push(int value) {
        if(value > 0){
            Node newNode = new Node(value, null);
            newNode.next = top;
            top = newNode;
        }
    }

    /**
     * -1 表示栈中没有数据
     */
    public int pop() {
        if (top == null) {
            return -1;
        }
        int value = top.data;
        top = top.next;
        return value;
    }

    public void printAll() {
        Node p = top;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public void clear(){
        top = null;
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }

    public static void main(String[] args) {
        StackBasedLinkedList mLinkedList = new StackBasedLinkedList();
        for (int i = 0; i < 9; i++) {
            mLinkedList.push(i);
        }
        mLinkedList.printAll();
        for (int i = 0; i < 3; i++) {
            mLinkedList.pop();
        }
        mLinkedList.printAll();
    }
}

package queue;

public class QueueBasedLinkedList {
    private static class Node{
        private String data;
        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;

    public void enqueue(String value){
        //只要是链表，就要注意边界问题：首次插入和删除剩下最后一个
        if (tail == null) {
            Node newNode = new Node(value, null);
            head = newNode;
            tail = newNode;
        } else {
            tail.next = new Node(value, null);
            tail = tail.next;
        }
    }

    public String dequeue(){
        if (head == tail) { //队列空
            return null;
        } else {
            String value = head.data;
            head = head.next;
            //???
            if (head == null) {
                tail = null;
            }
            return value;
        }
    }

    public void printAll(){
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }
}

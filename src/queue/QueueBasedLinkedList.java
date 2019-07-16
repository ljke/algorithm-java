package queue;

public class QueueBasedLinkedList {
    private static class Node {
        private String data;
        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;

    public void enqueue(String value) {
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

    public String dequeue() {
        if (head == tail) { //fixed: 此时表示队列仅剩一个元素
            String last = head.data;
            head = null;
            //删除了最后一个结点，此时将tail置为空，在enqueue中进行首次插入
            tail = null;
            return last;
        } else {
            String value = head.data;
            head = head.next;
            return value;
        }
    }

    public void printAll() {
        if (head == null) {
            System.out.println("Empty");
            return;
        }
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        QueueBasedLinkedList qbll = new QueueBasedLinkedList();
        qbll.enqueue("123");
        qbll.printAll();
        System.out.println(qbll.dequeue());
        qbll.printAll();
        qbll.enqueue("1234");
        qbll.enqueue("12345");
        System.out.println(qbll.dequeue());
        System.out.println(qbll.dequeue());
        qbll.printAll();
    }
}

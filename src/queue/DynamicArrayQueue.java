package queue;

public class DynamicArrayQueue {
    private String[] items;
    private int capacity = 0;

    private int head = 0;
    private int tail = 0;

    public DynamicArrayQueue(int capacity) {
        this.capacity = capacity;
        items = new String[capacity];
    }

    public boolean enqueue(String data) {
        if (tail == capacity) { //队尾没有空间
            if (head == 0) { //队列占满
                return false;
            } else {
                //数据搬移
                for (int i = head; i < tail; i++) {
                    items[i - head] = items[i];
                }
            }
            //搬移完之后重新更新head和tail
            tail -= head;
            head = 0;
        }
        items[tail] = data;
        tail++;
        return true;
    }

    public String dequeue() {
        if (head == tail) {
            return null;
        }
        String ret = items[head];
        head++;
        return ret;
    }

    public void printAll() {
        for (int i = head; i < tail; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}

package queue;

public class ArrayQueue {
    private String[] items;
    private int n = 0;

    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int n) {
        items = new String[n];
        this.n = n;
    }

    public boolean enqueue(String data){
        if (tail == n) {
            return false;
        } else {
            items[tail] = data;
            tail++;
            return true;
        }
    }

    public String dequeue(){
        if (head == tail) {
            return null;
        } else {
            String data = items[head];
            head++;
            return data;
        }
    }

    public void printAll(){
        for (int i = head; i < tail; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}

package queue;

public class CircularQueue {
    private String[] items;
    private int n = 0;

    private int head = 0;
    private int tail = 0;

    public CircularQueue(int n) {
        items = new String[n];
        this.n = n;
    }

    public boolean enqueue(String item){
        //会空闲一个位置
        //这是必要的，否则的话无法正确判断队列满，因为head == tail可能是队列满也可能是队列空
        if((tail + 1) % n == head){
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    public String dequeue(){
        if(head == tail){
            return null;
        }
        String ret = items[head];
        head = (head + 1) % n;
        return ret;
    }

    public void printAll(){
        if(n == 0){
            return;
        }else{
            for (int i = head; i % n != tail ; i++) {
                System.out.print(items[i] + " ");
            }
            System.out.println();
        }
    }
}

package stack;

import java.util.Iterator;

public class StackOfArray<Item> implements Iterable<Item> {
    //存储数据的数组
    Item[] a = (Item[]) new Object[1];
    //记录元素个数N
    int N = 0;

    //构造器
    public StackOfArray() {
    }

    //添加元素
    public void push(Item item) {
        //自动扩容
        if (N == a.length) {
            resize(2 * a.length);
        }
        a[N++] = item;
    }

    //删除元素
    public Item pop() {
        Item item = a[--N];
        a[N] = null;
        if (N > 0 && N == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int length) {
        Item[] temp = (Item[]) new Object[length];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public Item peek(){
        return a[N - 1];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return a[--i];
        }
    }
}

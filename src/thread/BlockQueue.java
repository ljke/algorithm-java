/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linjie
 * @version : BlockQueue.java, v 0.1 2022年07月06日 2:47 下午 linjie Exp $
 */
public class BlockQueue {
    /**
     * 容器
     */
    private List<Integer> container = new ArrayList<>();
    /**
     * 当前大小
     */
    private volatile int size;
    /**
     * 容量
     */
    private volatile int capacity;
    /**
     * 可重入锁
     */
    private Lock lock = new ReentrantLock();

    private final Condition isNull = lock.newCondition();

    private final Condition isFull = lock.newCondition();

    BlockQueue(int capacity) {
        this.capacity = capacity;
    }

    public void add(int data) {
        // lock要放在try-catch外面
        lock.lock();
        try {
            try {
                while (size >= capacity) {
                    System.out.println("阻塞队列满了");
                    isFull.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ++size;
            container.add(data);
            isNull.signal();
        } finally {
            lock.unlock();
        }
    }

    public int take() {
        lock.lock();
        try {
            try {
                while (size == 0) {
                    System.out.println("阻塞队列空了");
                    isNull.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            --size;
            int res = container.get(0);
            container.remove(0);
            isFull.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }
}
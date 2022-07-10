/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author linjie
 * @version : ThreadFactory.java, v 0.1 2022年07月06日 2:34 下午 linjie Exp $
 */
public class ThreadFactory {


    /**
     * 默认线程池中的线程的数量
     */
    private static final int WORK_NUM = 5;

    /**
     * 默认处理任务的数量
     */
    private static final int TASK_NUM = 100;

    /**
     * 线程数量
     */
    private int workNum;


    /**
     * 任务数量
     */
    private int taskNum;

    /**
     * 保存线程的集合
     */
    private final Set<WorkThread> workThreads;


    /**
     * 阻塞有序队列存放任务
     */
    private final BlockingQueue<Runnable> taskQueue;

    public ThreadFactory() {
        this(WORK_NUM, TASK_NUM);
    }

    public ThreadFactory(int workNum, int taskNum) {
        if (workNum <= 0) {
            workNum = WORK_NUM;
        }
        if (taskNum <= 0) {
            taskNum = TASK_NUM;
        }
        taskQueue = new ArrayBlockingQueue<>(taskNum);
        this.workNum = workNum;
        this.taskNum = taskNum;
        workThreads = new HashSet<>();
        //启动一定数量的线程数，从队列中获取任务处理
        for (int i = 0; i < workNum; i++) {
            WorkThread workThread = new WorkThread("thead_"+i);
            workThread.start();
            workThreads.add(workThread);
        }
    }

    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        System.out.println("ready close thread pool...");
        if (workThreads == null || workThreads.isEmpty()) {
            return;
        }
        for (WorkThread workThread : workThreads) {
            workThread.stopWork();
        }
        workThreads.clear();
    }

    private class WorkThread extends Thread{
        public WorkThread(String name) {
            super();
            setName(name);
        }

        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    Runnable runnable = taskQueue.take();//获取任务
                    System.out.println(getName()+" ready execute:" + runnable);
                    runnable.run();//执行任务
                } catch (Exception e) {
                    interrupt();
                    e.printStackTrace();
                }
            }
        }

        public void stopWork() {
            interrupt();
        }
    }
}
package com.atguigu.sync;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/28 17:10
 * @description TODO
 */
class Share {
    private int number = 1;

    /**
     * 判断，通知，干活
     *
     * @throws InterruptedException
     */
    public synchronized void incr() throws InterruptedException {
        // 判断
        //if (number != 0) {
        while (number != 0) {
            // wait()在哪里睡，就在唤醒
            this.wait();
        }
        // 如果number是0，就执行加1操作
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);

        // 通知其他线程
        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        // 判断
        //if (number != 1) { 存在虚假唤醒
        while (number != 1) {
            this.wait();
        }

        // 干活
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);

        // 通知其他线程
        this.notifyAll();
    }
}

public class ThreadDemo1 {
    /**
     * 创建多个线程，调用资源类的方法
     */
    public static void main(String[] args) {
        Share share = new Share();

        // 创建加1线程
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "INCR").start();

        // 创建减1线程
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DECR").start();

        // 创建加1线程
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "INC1").start();

        // 创建减1线程
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DEC1").start();
    }
}

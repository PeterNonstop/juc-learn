package com.atguigu.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/28 17:50
 * @description TODO
 */

/**
 * 创建资料类，在资源类中定义属性与方法
 */
class Share {
    private int number = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * +1
     */
    public void incr() throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (number != 0) {
                condition.await();
            }

            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);

            // 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * -1
     */
    public void decr() throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (number != 1) {
                condition.await();
            }

            // 干活
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);

            // 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadDemo2 {
    public static void main(String[] args) {
        Share share = new com.atguigu.lock.Share();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();
    }
}

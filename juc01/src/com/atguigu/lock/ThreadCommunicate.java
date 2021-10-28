package com.atguigu.lock;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/28 23:50
 * @description TODO
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 创建资源类
 */
class ShareResource {
    /**
     * 定义标志位
     * 1 AA; 2 BB; 3 CC
     */
    private int flag = 1;

    // 创建锁
    private Lock lock = new ReentrantLock();

    // 创建3个condition
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    /**
     * 打印5次，参数是第几轮
     */
    public void print5Times(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (flag != 1) {
                // 等待
                c1.await();
            }

            // 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数: " + loop);
            }

            // 通知
            flag = 2;
            // 通知BB线程
            c2.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印10次，参数是第几轮
     */
    public void print10Times(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (flag != 2) {
                // 等待
                c2.await();
            }

            // 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数: " + loop);
            }

            // 通知
            flag = 3;
            // 通知CC线程
            c3.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印15次，参数是第几轮
     */
    public void print15Times(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (flag != 3) {
                // 等待
                c3.await();
            }

            // 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数: " + loop);
            }

            // 通知
            flag = 1;
            // 通知AA线程
            c1.signal();
        } finally {
            lock.unlock();
        }
    }

}

public class ThreadCommunicate {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    shareResource.print5Times(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    shareResource.print10Times(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 1; i <=10 ; i++) {
                try {
                    shareResource.print15Times(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();
    }
}

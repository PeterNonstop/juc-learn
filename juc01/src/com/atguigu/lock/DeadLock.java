package com.atguigu.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 9:38
 * @description TODO
 */
public class DeadLock {
    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " 持有锁a,试图获取锁b");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁b");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " 持有锁b,试图获取锁a");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁a");
                }
            }
        },"B").start();
    }
}

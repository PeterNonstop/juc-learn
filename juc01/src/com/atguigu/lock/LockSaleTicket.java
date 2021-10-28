package com.atguigu.lock;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/28 16:32
 * @description TODO
 */

import java.util.concurrent.locks.ReentrantLock;

/**
 * 第一步 创建资料类，定义属性与方法
 */
class LockTicket {
    private int number = 30;

    // 创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        // 判断是否有票
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ": 卖出：" + (number--) + ", 剩下：" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}

public class LockSaleTicket {
    public static void main(String[] args) {
        LockTicket ticket = new LockTicket();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"CC").start();
    }
}

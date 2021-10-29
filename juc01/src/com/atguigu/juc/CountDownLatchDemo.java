package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 11:03
 * @description TODO
 */
public class CountDownLatchDemo {
    /**
     * 6个同学都离开教室后，班长锁门离开
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建CountDownLatch对象，设置初始值
        CountDownLatch countDownLatch = new CountDownLatch(6);

        // 6个同学离开教室后
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了教室。");

                // 计数
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        // 等待
        countDownLatch.await();

        // 班长锁门离开
        System.out.println(Thread.currentThread().getName() + " 班长锁门离开。");
    }
}

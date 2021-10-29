package com.atguigu.juc;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 15:20
 * @description TODO
 */

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 6辆汽车，3个停车位
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 创建Semaphore，设置许可数量
        Semaphore semaphore = new Semaphore(3);

        // 模拟6辆汽车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // 抢占车位
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() + " 抢到了车位。");

                    // 设置随机停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(6));

                    System.out.println(Thread.currentThread().getName() + "------离开了车位。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放许可
                    semaphore.release();
                }
            }, String.valueOf(i)).start();

        }
    }
}

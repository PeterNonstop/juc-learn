package com.atguigu.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 17:47
 * @description TODO
 */
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        // 一个线程池5个线程
        // ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

        // 一个线程池1个线程
        // ExecutorService threadPool1 = Executors.newSingleThreadExecutor();

        // 一个线程池可扩容线程
        ExecutorService threadPool1 = Executors.newCachedThreadPool();

        // 处理10个请求
        try {
            for (int i = 1; i <= 10 ; i++) {
                threadPool1.execute(()->{
                    System.out.println(Thread.currentThread().getName()+ " 办理业务");
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            threadPool1.shutdown();
        }
    }
}

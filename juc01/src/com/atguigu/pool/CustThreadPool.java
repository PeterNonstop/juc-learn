package com.atguigu.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 21:05
 * @description 自定义线程池
 */
public class CustThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        // 处理10个请求
        try {
            for (int i = 1; i <= 10 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+ " 办理业务");
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

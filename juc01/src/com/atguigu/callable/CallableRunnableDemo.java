package com.atguigu.callable;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 9:53
 * @description TODO
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现Runnable接口
 */
class MyRunnableThread implements Runnable {

    @Override
    public void run() {

    }
}

/**
 * 实现Callable接口
 */
class MyCallableThread implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+ " come in callable");

        return 200;
    }
}

/**
 * Callable Runnable
 */
public class CallableRunnableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Runnable接口创建线程
        new Thread(new MyRunnableThread(), "runnable").start();

        // Callable接口实现线程
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyCallableThread());

        // lam表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName()+ " come in callable");
            return 1024;
        });

        // 创建一个线程
        new Thread(futureTask2, "lucy").start();

        new Thread(futureTask1, "mary").start();

        //while (!futureTask2.isDone()) {
        //    System.out.println("wait......");
        //}

        // 调用FutureTask的get方法
        System.out.println(futureTask2.get());

        System.out.println(futureTask1.get());

        System.out.println(Thread.currentThread().getName()+ " come over");
    }
}

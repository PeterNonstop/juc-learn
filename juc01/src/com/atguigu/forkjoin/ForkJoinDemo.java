package com.atguigu.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 22:15
 * @description TODO
 */
class MyTask extends RecursiveTask<Integer> {
    // 拆分差值不能超过10
    private static final Integer VALUE = 10;

    // 拆分开始值
    private int begin;

    // 拆分结束值
    private int end;

    // 返回结果
    private int result;

    /**
     * @param begin
     * @param end
     */
    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }


    // 拆分与合并过程
    @Override
    protected Integer compute() {
        // 判断相加两个数值是否大于10
        if ((end - begin) <= VALUE) {
            // 相加操作
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else { // 进一步拆分
            // 获取中间值
            int middle = (begin + end) / 2;

            // 拆分左边
            MyTask task01 = new MyTask(begin, middle);

            // 拆分右边
            MyTask task02 = new MyTask(middle + 1, end);

            // 调用方法拆分
            task01.fork();
            task02.fork();

            // 合并结果
            result = task01.join() + task02.join();

        }
        return result;
    }
}

public class ForkJoinDemo {

    public static void main(String[] args) {
        // 创建MyTask对象
        MyTask myTask = new MyTask(0, 100);
        // 创建合并分支池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        try {
            // 获取合并之后的结果
            Integer result = forkJoinTask.get();
            System.out.println("result = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }

    }
}

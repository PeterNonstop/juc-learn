package com.atguigu.readwrite;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 15:56
 * @description TODO
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类
 */
class MyCache {
    // 创建map集合
    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 放数据
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        // 加锁
        rwLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " 正在写操作 " + key);

            // 暂停一会
            TimeUnit.MILLISECONDS.sleep(300);

            // 放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写完了 " + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            rwLock.writeLock().unlock();
        }

    }

    /**
     * 取数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        // 添加读锁
        rwLock.readLock().lock();

        Object result = null;

        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取操作 " + key);

            // 暂停一会
            TimeUnit.MILLISECONDS.sleep(300);

            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完了 " + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            rwLock.readLock().unlock();
        }

        return result;
    }

}

public class ReadWriterLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        // 创建线程放数据
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, "write " + String.valueOf(i)).start();
        }

        // 创建线程读数据
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.get(num + "");
            }, "read " + String.valueOf(i)).start();
        }
    }
}

package com.atguigu.lock;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/29 0:21
 * @description TODO
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * list集合线程不安全
 */
class UnsafeThead {
    public void listUnsafe() {
        // list集合线程不安全
        // List<String> list = new ArrayList<>();

        // 方案一: Vector
        // List<String> list = new Vector<>();

        // 方案二： Collections.synchronizedList(new ArrayList<>())
        // List<String> list = Collections.synchronizedList(new ArrayList<>());

        // 方案三：CopyOnWriteArrayList
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // 向集合中添加内容
                list.add(UUID.randomUUID().toString().substring(0, 8));

                // 向集合中获取内容
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }


    public void listHashSet() {
        // HashSet不安全
        // Set<String> set = new HashSet<>();

        // 解决方案：CopyOnWriteArraySet
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // 向集合中添加内容
                set.add(UUID.randomUUID().toString().substring(0, 8));

                // 向集合中获取内容
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    public void listHashMap() {
        // HashMap
        // HashMap<String, String> map = new HashMap<>();

        // 解决方案：ConcurrentHashMap
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                // 向集合中添加内容
                map.put(key, UUID.randomUUID().toString().substring(0, 8));

                // 向集合中获取内容
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * 线程不安全
 */
public class ListThreadDemo {
    public static void main(String[] args) {

        UnsafeThead unsafeThead = new UnsafeThead();
        //unsafeThead.listUnsafe();

        //unsafeThead.listHashSet();

        unsafeThead.listHashMap();
    }


}

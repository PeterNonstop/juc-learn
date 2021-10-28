package com.atguigu.sync;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/10/28 16:17
 * @description TODO
 */
class Ticket {
    // 属性
    private int number = 30;

    // 操作方法，卖票
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ": 卖出：" + (number--) + ", 剩下：" + number);
        }
    }

}
public class SaleTicket {
    public static void main(String[] args) {
        // 创建Ticket对象
        Ticket ticket = new Ticket();

        // 创建三个线程
        new Thread(new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "AA").start();

        new Thread(new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "BB").start();

        new Thread(new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "CC").start();
    }
}

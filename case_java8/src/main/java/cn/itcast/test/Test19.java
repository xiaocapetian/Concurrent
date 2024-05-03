package cn.itcast.test;

import cn.itcast.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * wait是对象来调用的,哪个线程持有了这个对象的锁,哪个线程进休息室
 *
 * sleep是线程来调,没让你这个线程放锁
 */
@Slf4j(topic = "c.Test19")
public class Test19 {

    static final Object lock = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("获得锁");
                try {
                    Thread.sleep(20000);
                    //lock.wait(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        Sleeper.sleep(1);
        synchronized (lock) {
            log.debug("获得锁");
        }
    }
}

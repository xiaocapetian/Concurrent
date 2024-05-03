package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.Test22v2")
public class Test22v2 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
                //lock.lock();  <--这种则是不可打断的
            } catch (InterruptedException e) {
                //e.printStackTrace();
                log.debug("等锁的过程中被打断");
                return;
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("获得了锁");
        t1.start();
        try {
            sleep(1);
            t1.interrupt();
            log.debug("执行打断t1");
        } finally {
            lock.unlock();
        }
    }
}

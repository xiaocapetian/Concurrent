package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

import static cn.itcast.n2.util.Sleeper.sleep;

/**
 * 用cas设计一个锁
 */
@Slf4j(topic = "c.Test42")
public class LockCas {
    // 0 没加锁
    // 1 加锁
    private AtomicInteger state = new AtomicInteger(0);

    public void lock() {
        while (true) {//获取锁不成功就在这空转,<<===这其实很有问题
            if (state.compareAndSet(0, 1)) {
                break;
            }
        }
    }

    public void unlock() {
        log.debug("unlock...");
        state.set(0);
    }

    public static void main(String[] args) {
        LockCas lock = new LockCas();
        new Thread(() -> {
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
                sleep(1);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            log.debug("begin...");
            lock.lock();
            try {
                log.debug("lock...");
            } finally {
                lock.unlock();
            }
        }).start();
    }
}

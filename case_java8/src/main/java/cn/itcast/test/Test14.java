package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.Test14")
public class Test14 {
    public static void main(String[] args) throws InterruptedException {
        test3();
    }
    private static void test3() throws InterruptedException {
        //park()让线程停下来,如果打断标记是 true, 可以让 park 失效
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.interrupted());
            //👆如果这样就可再次park()
            //log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            LockSupport.park();
            log.debug("unpark...");
        }, "t1");
        t1.start();

        sleep(1);
        t1.interrupt();

    }
    private static void test4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();


        sleep(1);
        t1.interrupt();
    }




}

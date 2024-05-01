package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test6")
public class Test6 {
//18:28:15.698 c.Test6 [main] - t1 state: RUNNABLE
//18:28:16.214 c.Test6 [main] - t1 state: TIMED_WAITING
    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        log.debug("t1 state: {}", t1.getState());
        //这个时候t1线程还没走到Thread.sleep(2000);那一步呢

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state: {}", t1.getState());
    }
}

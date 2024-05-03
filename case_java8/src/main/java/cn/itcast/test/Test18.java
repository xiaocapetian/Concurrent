package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test18")
public class Test18 {
    static final Object lock = new Object();
    public static void main(String[] args) {

        trytoWait();
        test2();
    }

    private static void test2() {
        synchronized (lock) {
            trytoWait();
        }
    }

    private static void trytoWait() {
        try {
            lock.wait();//获得对象锁后,现在可以去休息室waitset休息去了
            //没获取对象锁是没资格去waitset的
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

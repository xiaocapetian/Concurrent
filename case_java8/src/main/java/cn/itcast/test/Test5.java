package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test5")
public class Test5 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        System.out.println(t1.getState());//看线程的状态信息
        t1.start();
        System.out.println(t1.getState());
        //NEW
        //RUNNABLE(strat()之后
    }
}

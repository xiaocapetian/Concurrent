package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        //任务
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        //简化
        Runnable r2 = () -> {log.debug("running");};
        //把任务给线程
        Thread t = new Thread(r2, "t2");
        //再简化
        Thread t2 = new Thread(()->{
            log.debug("running");
        });
        t.start();
    }
}

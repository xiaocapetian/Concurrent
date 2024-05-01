package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.Test2FutureTask")
public class Test2FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(1000);
                return 100;
            }
        });
        Thread t1 = new Thread(task,"t1");
        t1.start();
        //task.get()会阻塞等待结果
        log.debug("{}",task.get());

    }
}

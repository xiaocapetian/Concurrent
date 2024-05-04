package cn.itcast.n8;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestTimer")
public class P224TestTimer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        //method1();
        method3();
        //method4();

        //method5();
    }

    private static void method4() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.schedule(() -> {
            try {
                log.debug("task1");
                int i = 1 / 0;
            } catch (Exception e) {
                log.error("error:", e);
            }
        }, 1, TimeUnit.SECONDS);
    }

    private static void method5() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(() -> {
            try {
                log.debug("task1");
                int i = 1 / 0;
            } catch (Exception e) {
                log.error("error:", e);
            }
        });
    }

    private static void method3() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {//翻译为以固定的速率执行任务schedule At Fixed Rate(实际执行时间超出了就无缝执行)
            log.debug("running...");
        }, 1, 1, TimeUnit.SECONDS);

        pool.scheduleWithFixedDelay(() -> {//翻译为以固定的间隔执行,
            log.debug("running2...");
        }, 1, 1, TimeUnit.SECONDS);
    }

    private static void method2(ScheduledExecutorService pool) {
        pool.schedule(() -> {
            log.debug("task1");
            int i = 1 / 0;
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
        //如果一个任务出异常了,后续任务还是可以执行
    }

    private static void method1() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
                sleep(2);
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };

        log.debug("start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }
}

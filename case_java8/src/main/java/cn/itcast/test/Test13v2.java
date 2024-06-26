package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 用volatile实现两阶段终止模式 优雅退出
 */
@Slf4j(topic = "c.TwoPhaseTermination")
public class Test13v2 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        tpt.start();
        tpt.start();

        Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false;
    //====只需要一个监控线程
    // 判断是否执行过 start 方法
    private boolean starting = false;

    // 启动监控线程---只希望执行一次
    public void start() {
        synchronized (this) {//防止2个线程都进来
            if (starting) { // false
                return;
            }
            starting = true;
        }
        monitorThread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                // 是否被打断
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    // 停止监控线程
    public void stop() {
        stop = true;
        monitorThread.interrupt();//加了这个会让正在睡的它立刻醒来去比stop.不加的会就会睡满1s
    }
}

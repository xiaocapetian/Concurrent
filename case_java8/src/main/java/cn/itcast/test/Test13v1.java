package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;
//不使用volatile时,
@Slf4j(topic = "c.T13")
public class Test13v1 {
    public static void main(String[] args) throws InterruptedException {
        TPTInterrupt tpt = new TPTInterrupt();
        tpt.start();
        Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();
    }
}
@Slf4j(topic = "c.TwoPhaseTermination")
class TPTInterrupt {
    private Thread thread;
    public void start(){
        thread = new Thread(() -> {
            while(true) {
                Thread current = Thread.currentThread();
                if(current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    //再两个可能会被打断
                    Thread.sleep(1000);//可能1--这个时候current.isInterrupted()会重置为false
                    log.debug("执行监控记录");//可能2
                } catch (InterruptedException e) {
                    //如果正睡着被打断了会进入这里
                    //e.printStackTrace();
                    current.interrupt();
                }
                // 执行监控操作
            }
        },"监控线程");
        thread.start();
    }
    public void stop() {
        thread.interrupt();
    }
}


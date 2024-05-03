package cn.itcast.n4.exercise;

//import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

//@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {
    public static void main(String[] args) throws InterruptedException {
        // 模拟多人买票
        TicketWindow window = new TicketWindow(2000);

        // 所有线程的集合
        List<Thread> threadList = new ArrayList<>();
        // 卖出的票数统计---这里用到是线程安全的list
        List<Integer> amountList = new Vector<>();
        for (int i = 0; i < 4000; i++) {
            Thread thread = new Thread(() -> {
                // 买票
                int amount = window.sell(random(5));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 统计买票数
                amountList.add(amount);
            });
            threadList.add(thread);
            thread.start();
        }
//对于主线程来说,上面的for转完才会到这里,所以threadList以及放了2000个线程了
        for (Thread thread : threadList) {
            thread.join();
        }

        // 统计卖出的票数和剩余票数
        System.out.println("余票："+window.getCount());
        System.out.println("卖出的票数："+ amountList.stream().mapToInt(i-> i).sum());
        //log.debug("余票：{}",window.getCount());
        //log.debug("卖出的票数：{}", amountList.stream().mapToInt(i-> i).sum());
    }

    // Random 为线程安全
    static Random random = new Random();

    // 随机 1~5
    public static int random(int amount) {
        return random.nextInt(amount) + 1;
    }
}

// 售票窗口
class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    // 获取余票数量
    public int getCount() {
        return count;
    }

    // 售票
    public synchronized int sell(int amount) {
    //public int sell(int amount) {//这就有线程安全问题了
        if (this.count >= amount) {
            this.count -= amount;
            return amount;//卖了多少票要返回
        } else {
            return 0;
        }
    }
}
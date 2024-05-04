package cn.itcast.n8.TestPoolltt;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {
    // 1. 任务队列
    private Deque<T> queue = new ArrayDeque<>();
    // 2. 锁
    private ReentrantLock lock = new ReentrantLock();
    //为什么要锁,一个任务只能被一个线程获取,不能被几个线程获取了
    //用锁保护队列头和队列尾的元素

    // 3. 生产者条件变量
    private Condition fullWaitSet = lock.newCondition();

    // 4. 消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();

    // 5. 容量
    private int capcity;

    // 阻塞获取
    public T poll() {

        return null;
    }
    // 阻塞添加
    public void put(T task) {

    }
}

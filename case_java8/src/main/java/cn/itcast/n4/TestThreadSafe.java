package cn.itcast.n4;

import cn.itcast.test.TestUnsafe;

import java.util.ArrayList;

public class TestThreadSafe {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;
    public static void main(String[] args) {
        //ThreadUnsafe test = new ThreadUnsafe();
        ThreadSafeSubClass test = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i+1)).start();
        }
        //test.method3(new ArrayList<>());
    }
}
class ThreadUnsafe {
    ArrayList<String> list = new ArrayList<>();//这个时候不同的线程共享这个list
    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }
//一个线程往里加
    private void method2() {
        list.add("1");
    }
//一个删
    private void method3() {
        list.remove(0);
    }
}

class ThreadSafe {
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>();//这个时候不同的线程到这里都会创建一个list,不共享
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    private void method2(ArrayList<String> list) {
        list.add("1");
    }

    private void method3(ArrayList<String> list) {
        System.out.println(1);
        list.remove(0);
    }
}

class ThreadSafeSubClass extends ThreadSafe{
    //@Override
    public void method3(ArrayList<String> list) {
        System.out.println(2);
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}
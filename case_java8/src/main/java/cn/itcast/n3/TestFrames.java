package cn.itcast.n3;

public class TestFrames {
    public static void main(String[] args) {
        //这里主线程和t1线程都会走,且可看到是互不相干的
        //也就是说,每个线程都会有自己的栈
        Thread t1 = new Thread(){
            @Override
            public void run() {
                method1(20);
            }
        };
        t1.setName("t1");
        t1.start();
        method1(10);
    }

    private static void method1(int x) {
        int y = x + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object n = new Object();
        return n;
    }
}

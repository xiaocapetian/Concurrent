package cn.itcast.test;


import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.TestBiased")
public class TestBiased {

    static Thread t1,t2,t3;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        Dog d = new Dog();
        log.debug(ClassLayout.parseInstance(d).toPrintable(d));
        /*
        15:03:47.829 c.TestBiased [main] - cn.itcast.test.Dog object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           6a 7a 01 f8 (01101010 01111010 00000001 11111000) (-134120854)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
OFFSET列表示偏移量，即从对象开始到某个特定字段的字节偏移量。
SIZE列表示该字段的大小，以字节为单位。
TYPE DESCRIPTION列提供了字段的类型和描述。
VALUE列显示了字段的值。
32 bits:
-----------------------------------
|             Mark Word            |
-----------------------------------
| lock:2 | biased_lock:1 | age:4 |
| epoch:2| unused:1 | ptr_to_lock_record:62 |
-----------------------------------

         */
    }
    private static void test4() throws InterruptedException {

        Vector<Dog> list = new Vector<>();

        int loopNumber = 38;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopNumber; i++) {
                Dog d = new Dog();
                list.add(d);
                synchronized (d) {
                    log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(d));
                }
            }
            LockSupport.unpark(t2);
        }, "t1");
        t1.start();

        t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
               // log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(true));
                synchronized (d) {
                    //log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(true));
                }
                //log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(true));
            }
            LockSupport.unpark(t3);
        }, "t2");
        t2.start();

        t3 = new Thread(() -> {
            LockSupport.park();
            log.debug("===============> ");
            for (int i = 0; i < loopNumber; i++) {
                Dog d = list.get(i);
                //log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(true));
                synchronized (d) {
                    //log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(true));
                }
               // log.debug(i + "\t" + ClassLayout.parseInstance(d).toPrintable(true));
            }
        }, "t3");
        t3.start();

        t3.join();
        //log.debug(ClassLayout.parseInstance(new Dog()).toPrintable(true));
    }
}

class Dog {

}

package cn.itcast.test;

import cn.itcast.n4.UnsafeAccessor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * 尝试着自己写一个原子整数类[原理学习]
 */
@Slf4j(topic = "c.Test42")
public class Test42 {
    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(10000));
    }
}

class MyAtomicInteger implements Account {
    private volatile int value;//为什么加volatile?要和cas配合
    private static final long valueOffset;
    private static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();//这是我们自己写的封装的一个工具类
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        while(true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}

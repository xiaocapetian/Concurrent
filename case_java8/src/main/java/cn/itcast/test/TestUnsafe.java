package cn.itcast.test;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnsafe {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");//用反射获取私有成员变量theUnsafe
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        //获取哪个对象的成员变量theUnsafe值呀?传一个null的原因是这个成员变量是静态的,不需要传对象

        System.out.println(unsafe);

        // 1. 获取(某个类的)域的偏移地址,Teacher的id是一个域
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        // 2. 执行 cas 操作,改值
        unsafe.compareAndSwapInt(teacher, idOffset, 0, 1);
        unsafe.compareAndSwapObject(teacher, nameOffset, null, "张三");

        // 3. 验证
        System.out.println(teacher);
    }
}
@Data
class Teacher {
    volatile int id;
    volatile String name;
}



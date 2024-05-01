package cn.itcast.n4;

import java.util.ArrayList;
import java.util.List;

public class TestJiCheng {
    public static void main(String[] args) {
        Animal animal = new Cat();
        animal.intro();//会执行子类重写的方法
        /*animal.intro2();//编译不了,private你是访问不到的*/
        animal.in2();//animal2,in2会调用父类自己的intro2(),而不是子类的同名的intro2()(这不是重写,而是同名方法)
        animal.intro3();
        //animal.miao();//父类没有miao()调用不了
        Cat cat = new Cat();
        cat.intro();//会执行子类重写的方法
        cat.in2();//animal2 in2会调用父类自己的intro2(),而不是子类的同名的intro2()(这不是重写,而是同名方法)
        cat.intro2();//cat2 会调子类自己的同名方法,注意:并不是重写，而是一个新的方法
        cat.intro3();//调父类的animal3,没问题
        cat.miao();//miao没问题
        final Cat cg = new Cat(2);
        cg.age = 3;
        System.out.println(cg.age);
    }
}
class Animal{
    public void intro(){
        System.out.println("animal");
        ArrayList<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
    }

    private void intro2(){
        System.out.println("animal2");
    }
    public void in2(){
        intro2();
    }
    public final void intro3(){
        System.out.println("animal3");
    }
}
class Cat extends Animal{
    int age;
    Cat(){}
    Cat(int age){
        age = age;
    }
    @Override
    public void intro() {
        super.intro();
        System.out.println("再写一个cat");
    }
    //intro2()是private 这时候虽然不能加@Override,但是可以再写一个
    public void intro2() {
        System.out.println("cat2");
    }
    //被声明为 final，所以无法在子类中重写，即使在子类中定义了同名的方法也会报错。
//    public void intro3() {
//        System.out.println("cat3");
//    }
    public void miao(){
        System.out.println("miao");
    }
}

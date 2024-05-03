package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j(topic = "c.Test35")
public class Test35AtomicReference {
    public static void main(String[] args) {
        DecimalAccount.demo(new DecimalAccountCas(new BigDecimal("10000")));
    }
}

class DecimalAccountCas implements DecimalAccount {
    //原子引用类
    //private BigDecimal balance;
    //泛型就是要保护的对象
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal balance) {
//        this.balance = balance;
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while(true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            //获取值,减法,设置值,实际上是三个步骤
            //BigDecimal类本身是不可变类,上面三步骤的每一步都是线程安全的,但是组合在一起就不安全了,需要CAS
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
    
}

interface DecimalAccount {
    // 获取余额
    BigDecimal getBalance();

    // 取款
    void withdraw(BigDecimal amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}
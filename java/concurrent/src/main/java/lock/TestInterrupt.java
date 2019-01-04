package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestInterrupt {
    final static Lock lock = new ReentrantLock();
    static int  index = 0;
    public static void main(String[] args) {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("thread-0");
                while (true){
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + index);
                        index ++;
                        if (Thread.currentThread().isInterrupted()){
                            System.out.println(Thread.currentThread().getName() + "isInterrupted");
                        }
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }finally {
                        lock.unlock();
                    }
                }

            }
        });

        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("thread-1");
                while (true){
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + index);
                        index ++;
                        if (Thread.currentThread().isInterrupted()){
                            System.out.println(Thread.currentThread().getName() + "isInterrupted");
                        }
                        //TimeUnit.SECONDS.sleep(1);

                    }finally {
                        lock.unlock();
                    }
                }

            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("thread-2");
                while (true){
                    try {
                        lock.lockInterruptibly();
                        System.out.println(Thread.currentThread().getName() + index);
                        index ++;
                        TimeUnit.SECONDS.sleep(2);
                        t.interrupt();
                        t1.interrupt();
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() +"InterruptedException");
                    }finally {
                        lock.unlock();
                    }
                }

            }
        });

        t.start();
        t1.start();
        t2.start();

    }
}

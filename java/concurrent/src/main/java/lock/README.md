1.问题:关于锁的中断，lockInterruptibly()可以立即响应中断，lock()会设置线程中断状态，但是不会中断等待，会到中断点才回中断（比如sleep）
  测试类：
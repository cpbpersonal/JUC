package aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyAqsLock implements Lock {
    
    private Sync sync=new Sync();
    @Override
    public void lock() {sync.acquire(1); }
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }
    @Override
    public boolean tryLock() { return sync.tryAcquire(1); }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
    @Override
    public void unlock() { sync.tryRelease(1);}
    @Override
    public Condition newCondition() {
        return null;
    }

    private class Sync extends AbstractQueuedSynchronizer{
    @Override//这两个方法被Acquire和release调用，由AQS框架实现
    protected boolean tryAcquire(int arg) {
        assert  arg==1;//尝试获取锁，如果获取成功把拥有者设置为当前线程
        if(compareAndSetState(0,1)){
            setExclusiveOwnerThread(Thread.currentThread());
            return  true;
        }
       return  false;
    }
    @Override
    protected boolean tryRelease(int arg) {
       assert arg==1;
       if(! isHeldExclusively())//尝试释放锁，如果该锁的拥有者是自己，释放锁。
           setExclusiveOwnerThread(null);
           setState(0);
           return true;
    }
    @Override
    protected boolean isHeldExclusively() {
        return getExclusiveOwnerThread()==Thread.currentThread();
    }
 }
}

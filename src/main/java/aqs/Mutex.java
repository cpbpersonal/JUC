package aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class Mutex implements Lock, java.io.Serializable {
    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    } // Our internal helper class private static class Sync extends AbstractQueuedSynchronizer { // Reports whether in locked state protected boolean isHeldExclusively() { return getState() == 1; } // Acquires the lock if state is zero public boolean tryAcquire(int acquires) { assert acquires == 1; // Otherwise unused if (compareAndSetState(0, 1)) { setExclusiveOwnerThread(Thread.currentThread()); return true; } return false; } // Releases the lock by setting state to zero protected boolean tryRelease(int releases) { assert releases == 1; // Otherwise unused if (getState() == 0) throw new IllegalMonitorStateException(); setExclusiveOwnerThread(null); setState(0); return true; } // Provides a Condition Condition newCondition() { return new ConditionObject(); } // Deserializes properly private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException { s.defaultReadObject(); setState(0); // reset to unlocked state } } // The sync object does all the hard work. We just forward to it. private final Sync sync = new Sync(); public void lock() { sync.acquire(1); } public boolean tryLock() { return sync.tryAcquire(1); } public void unlock() { sync.release(1); } public Condition newCondition() { return sync.newCondition(); } public boolean isLocked() { return sync.isHeldExclusively(); } public boolean hasQueuedThreads() { return sync.hasQueuedThreads(); } public void lockInterruptibly() throws InterruptedException { sync.acquireInterruptibly(1); } public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException { return sync.tryAcquireNanos(1, unit.toNanos(timeout)); } } 这是一个类似CountDownLatch的闩锁类，只是它只需要一个signal才能触发。 因为锁存器是非排他的，它使用shared获取和释放方法。

    class BooleanLatch { private  class Sync extends AbstractQueuedSynchronizer {
        boolean isSignalled() { return getState() != 0; }
        protected int tryAcquireShared(int ignore) { return isSignalled() ? 1 : -1; }
        protected boolean tryReleaseShared(int ignore) { setState(1); return true; } }
        private final Sync sync = new Sync(); public boolean isSignalled() { return sync.isSignalled(); }
        public void signal() { sync.releaseShared(1); }
        public void await() throws InterruptedException
        { sync.acquireSharedInterruptibly(1); } }
}

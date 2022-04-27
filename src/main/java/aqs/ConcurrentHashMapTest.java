package aqs;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("aa","aa1");
        map.size();
    }}
/*
    final V putVal(K key, V value, boolean onlyIfAbsent) {
        //插入的key和value都不能为空
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());
        int binCount = 0;
        for (ConcurrentHashMap.Node<K,V>[] tab = table;;) {
            ConcurrentHashMap.Node<K,V> f; int n, i, fh;
            //如果hash表为空进行初始化
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();
            //f 即为当前 key 定位出的 Node，如果为空表示当前位置可以写入数据，利用 CAS 尝试写入，失败则自旋保证成功。
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                if (casTabAt(tab, i, null,
                        new ConcurrentHashMap.Node<K,V>(hash, key, value, null)))
                    break;                   // no lock when adding to empty bin
            }
            //f.hash==MOVED(-1)说明当前位置正在进行扩容，该线程不能进行插入操作，取帮助扩容
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                //当前node不为空，锁住当前节点进行插入操作
                synchronized (f) {
                    if (tabAt(tab, i) == f) {//再次判断确保加锁时没有被改变
                        if (fh >= 0) {
                            binCount = 1;
                            for (ConcurrentHashMap.Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                        ((ek = e.key) == key ||
                                                (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                ConcurrentHashMap.Node<K,V> pred = e;
                                if ((e = e.next) == null) {
                                    pred.next = new ConcurrentHashMap.Node<K,V>(hash, key,
                                            value, null);
                                    break;
                                }
                            }
                        }//当前节点是一个TreeBin对象，进行红黑树的插入插入操作
                        else if (f instanceof ConcurrentHashMap.TreeBin) {
                            ConcurrentHashMap.Node<K,V> p;
                            binCount = 2;
                            if ((p = ((ConcurrentHashMap.TreeBin<K,V>)f).putTreeVal(hash, key,
                                    value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                if (binCount != 0) {//判断是否需要转为红黑树
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }//计数进行扩容判断
        addCount(1L, binCount);
        return null;
    }
}






    private final void transfer(ConcurrentHashMap.Node<K,V>[] tab, ConcurrentHashMap.Node<K,V>[] nextTab) {
        int n = tab.length, stride;//计算步长  cpu>1 步长为 ：表的大小/8/cpu个数<16就赋值为16
        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
            stride = MIN_TRANSFER_STRIDE; // subdivide range ==16
        if (nextTab == null) {        //初始化一个新数组    // initiating
            try {
                @SuppressWarnings("unchecked")
                ConcurrentHashMap.Node<K,V>[] nt = (ConcurrentHashMap.Node<K,V>[])new ConcurrentHashMap.Node<?,?>[n << 1];
                nextTab = nt;
            } catch (Throwable ex) {      // try to cope with OOME
                sizeCtl = Integer.MAX_VALUE;
                return;
            }
            nextTable = nextTab;
            transferIndex = n;
        }
        int nextn = nextTab.length;
        ConcurrentHashMap.ForwardingNode<K,V> fwd = new ConcurrentHashMap.ForwardingNode<K,V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        for (int i = 0, bound = 0;;) {
            ConcurrentHashMap.Node<K,V> f; int fh;
            while (advance) {//i--->bound进行转移
                int nextIndex, nextBound;
                if (--i >= bound || finishing)
                    advance = false;
                //transferindex小于0说明数组分配完毕
                else if ((nextIndex = transferIndex) <= 0) {
                    i = -1;
                    advance = false;
                }//计算每个线程转移的区间
                else if (U.compareAndSwapInt
                        (this, TRANSFERINDEX, nextIndex,
                                nextBound = (nextIndex > stride ?
                                        nextIndex - stride : 0))) {
                    bound = nextBound;
                    i = nextIndex - 1;
                    advance = false;
                }
            }
            if (i < 0 || i >= n || i + n >= nextn) {
                int sc;
                if (finishing) {//完成进行赋值
                    nextTable = null;
                    table = nextTab;
                    sizeCtl = (n << 1) - (n >>> 1);
                    return;
                }//每个区间完成sc-1；全部完成，finishing=true；
                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                        return;
                    finishing = advance = true;
                    i = n; // recheck before commit
                }
            }//将正在转移的节点设置为fwd
            else if ((f = tabAt(tab, i)) == null)
                advance = casTabAt(tab, i, null, fwd);
            else if ((fh = f.hash) == MOVED)
                advance = true; // already processed
            else {//转移链表或红黑树上的节点。
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        ConcurrentHashMap.Node<K,V> ln, hn;
                        if (fh >= 0) {
                            int runBit = fh & n;
                            ConcurrentHashMap.Node<K,V> lastRun = f;
                            for (ConcurrentHashMap.Node<K,V> p = f.next; p != null; p = p.next) {
                                int b = p.hash & n;
                                if (b != runBit) {
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            if (runBit == 0) {
                                ln = lastRun;
                                hn = null;
                            }
                            else {
                                hn = lastRun;
                                ln = null;
                            }
                            for (ConcurrentHashMap.Node<K,V> p = f; p != lastRun; p = p.next) {
                                int ph = p.hash; K pk = p.key; V pv = p.val;
                                if ((ph & n) == 0)
                                    ln = new ConcurrentHashMap.Node<K,V>(ph, pk, pv, ln);
                                else
                                    hn = new ConcurrentHashMap.Node<K,V>(ph, pk, pv, hn);
                            }
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                        else if (f instanceof ConcurrentHashMap.TreeBin) {
                            ConcurrentHashMap.TreeBin<K,V> t = (ConcurrentHashMap.TreeBin<K,V>)f;
                            ConcurrentHashMap.TreeNode<K,V> lo = null, loTail = null;
                            ConcurrentHashMap.TreeNode<K,V> hi = null, hiTail = null;
                            int lc = 0, hc = 0;
                            for (ConcurrentHashMap.Node<K,V> e = t.first; e != null; e = e.next) {
                                int h = e.hash;
                                ConcurrentHashMap.TreeNode<K,V> p = new ConcurrentHashMap.TreeNode<K,V>
                                        (h, e.key, e.val, null, null);
                                if ((h & n) == 0) {
                                    if ((p.prev = loTail) == null)
                                        lo = p;
                                    else
                                        loTail.next = p;
                                    loTail = p;
                                    ++lc;
                                }
                                else {
                                    if ((p.prev = hiTail) == null)
                                        hi = p;
                                    else
                                        hiTail.next = p;
                                    hiTail = p;
                                    ++hc;
                                }
                            }
                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                                    (hc != 0) ? new ConcurrentHashMap.TreeBin<K,V>(lo) : t;
                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                                    (lc != 0) ? new ConcurrentHashMap.TreeBin<K,V>(hi) : t;
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                    }
                }
            }
        }
    }*/

package com.example.recyclerviewexample.java;

import android.util.LruCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * LinkedHashMap = HashMap + 双向链表。
 *
 * 应用场景：
 * LinkedHashMap主要用于需要知道访问顺序的场景，比如LruCache中，需要按put顺序或者访问时间顺序进行数据维护。
 * 又或者需要使用定长的缓存。
 *
 * JDK1.8以上就是链表+红黑树。
 * 而LinkedHashMap的不同之处就在于它又多维护了一个双向链表。
 */
public class LinkedHashMapDemo {

    private static final String TAG = "maomaoLinkedHashMapDemo";


    public void LinkedHashMapTest() {
//        实际打印的结果也是3条数据，因此初始化的2并不代表LinkedHashMap的最大容量。
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(2);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(3, 3);
        Set<Map.Entry<Integer, Integer>> entrySet = linkedHashMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            System.out.println("LinkedHashMapTest: key=" + entry.getKey());
            System.out.println("LinkedHashMapTest: value=" + entry.getValue());
        }
    }

    /**
     * Java匿名内部类访问外部变量，为何需被标志为final？
     * <p>
     * 这要从闭包说起，匿名内部类和外部方法形成了一个闭包，因此，匿名内部类能够访问外部方法的变量，看起来是一种“天经地义”的事情，Java语言当然也需要实现这种特性，但是这里遇到了一个问题。
     * <p>
     * 匿名内部类的生命周期可能比外部的类要长，因此访问外部局部变量有可能是访问不到的。
     * <p>
     * 那怎么办呢？Java语言为了实现这种特性， 只好将外部的局部变量偷偷的赋值了一份给匿名内部类。那这样匿名内部类就可以肆无忌惮的访问外部局部变量了。
     * <p>
     * 问题又来了，这种通过赋值的形式有一个缺陷，匿名内部类不可以修改“原来的局部变量”，因为是一份“复制品”，修改复制品对原变量没什么影响啊。
     * <p>
     * 那怎么办？ Java语言干脆强制要求被匿名内部类访问的外部局部变量必须是final的，什么意思呢？就是“一刀切”，不让修改了。
     */
    LinkedHashMap<Integer, Integer> linkedHashMap;

    public void LinkedHashMapTest2() {
        linkedHashMap = new LinkedHashMap<Integer, Integer>(2) {
            @Override
            protected boolean removeEldestEntry(Entry eldest) {
                return linkedHashMap.size() > 2;
            }
        };
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(3, 3);
        Set<Map.Entry<Integer, Integer>> entrySet = linkedHashMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            System.out.println("LinkedHashMapTest: key=" + entry.getKey());
            System.out.println("LinkedHashMapTest: value=" + entry.getValue());
        }
    }

    public void LinkedHashMapTest3() {
        linkedHashMap = new LinkedHashMap<Integer, Integer>(2) {
            @Override
            protected boolean removeEldestEntry(Entry eldest) {
                return linkedHashMap.size() > 2;
            }
        };
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
//        还是以上的例子，我们在put完以后调用了get(1)再打印日志发现并没有按我们预想的顺序将1放在最后，
//        因为LinkedHashMap按访问顺序排序默认是关闭的，可通过构造函数实例化的时候打开
        linkedHashMap.get(1);
        Set<Map.Entry<Integer, Integer>> entrySet = linkedHashMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            System.out.println("LinkedHashMapTest: key=" + entry.getKey());
            System.out.println("LinkedHashMapTest: value=" + entry.getValue());
        }
    }

    public void LinkedHashMapTest4() {
        linkedHashMap = new LinkedHashMap<Integer, Integer>(2, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Entry eldest) {
                return linkedHashMap.size() > 2;
            }
        };
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
//        还是以上的例子，我们在put完以后调用了get(1)再打印日志发现并没有按我们预想的顺序将1放在最后，
//        因为LinkedHashMap按访问顺序排序默认是关闭的，可通过构造函数实例化的时候打开
        linkedHashMap.get(1);
        Set<Map.Entry<Integer, Integer>> entrySet = linkedHashMap.entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            System.out.println("LinkedHashMapTest: key=" + entry.getKey());
            System.out.println("LinkedHashMapTest: value=" + entry.getValue());
        }
    }

    public void lruCacheTest() {
        LruCache<Integer, Integer> lruCache = new LruCache<>(4);
        lruCache.put(1,1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        lruCache.put(4,4);
        lruCache.put(5,5);
        Set<Map.Entry<Integer, Integer>> entrySet = lruCache.snapshot().entrySet();
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            System.out.println("LinkedHashMapTest: key=" + entry.getKey());
            System.out.println("LinkedHashMapTest: value=" + entry.getValue());
        }

        lruCache.put(5,5);
        System.out.println("超出设定存储容量后");
        for (Map.Entry<Integer, Integer> entry : entrySet) {
            System.out.println("LinkedHashMapTest: key=" + entry.getKey());
            System.out.println("LinkedHashMapTest: value=" + entry.getValue());
        }
    }


    public static void main(String[] args) {
        LinkedHashMapDemo linkedHashMapDemo = new LinkedHashMapDemo();
        linkedHashMapDemo.LinkedHashMapTest();
        System.out.println("============LinkedHashMapTest2=========");
        linkedHashMapDemo.LinkedHashMapTest2();
        System.out.println("============LinkedHashMapTest3=========");
        linkedHashMapDemo.LinkedHashMapTest3();
        System.out.println("============LinkedHashMapTest4=========");
        linkedHashMapDemo.LinkedHashMapTest4();
        System.out.println("============lruCacheTest=========");
        linkedHashMapDemo.lruCacheTest();
    }
}

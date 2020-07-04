package com.example.recyclerviewexample.java.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    static abstract class AbstractDataRunnable implements Runnable {

        private final CountDownLatch countDownLatch;
        private final String name;

        public AbstractDataRunnable(String name, CountDownLatch countDownLatch) {
            this.name = name;
            this.countDownLatch = countDownLatch;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.getName() + " 开始加载...");
                Long l1 = System.currentTimeMillis();
                handle();
                Long l2 = System.currentTimeMillis();
                System.out.println(this.getName() + " 加载完成,花费时间:" + (l2 - l1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.countDownLatch.countDown();
            }
            afterCountDown();
        }

        protected void afterCountDown() {
            System.out.println(this.getName() + ":CountDownLatch计数减一之后,继续加载其他数据...");
        }

        protected abstract void handle() throws InterruptedException;
    }

    static class BackGroundData extends AbstractDataRunnable {

        public BackGroundData(String name, CountDownLatch countDownLatch) {
            super(name, countDownLatch);
        }

        @Override
        public void handle() throws InterruptedException {
            Thread.sleep(2000);
        }
    }

    static class GoodsData  extends AbstractDataRunnable {

        public GoodsData (String name, CountDownLatch countDownLatch) {
            super(name, countDownLatch);
        }

        @Override
        public void handle() throws InterruptedException {
            Thread.sleep(2500);
        }
    }

    static class MapData extends AbstractDataRunnable {

        public MapData(String name, CountDownLatch countDownLatch) {
            super(name, countDownLatch);
        }

        @Override
        public void handle() throws InterruptedException {
            Thread.sleep(3000);
        }
    }

    static class StartGame implements Runnable {

        private final CountDownLatch countDownLatch;

        public StartGame(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始加载基础数据...");
                Long l1 = System.currentTimeMillis();
                countDownLatch.await();
                Long l2 = System.currentTimeMillis();
                System.out.println("基础数据加载完毕，总共花费时长:"+(l2-l1)+".可以开始游戏...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread startGame = new Thread(new StartGame(countDownLatch));
        startGame.start();

        Thread thread = new Thread(new BackGroundData("BackGroundData", countDownLatch));
        Thread thread2 = new Thread(new GoodsData("GoodsData", countDownLatch));
        Thread thread3 = new Thread(new MapData("MapData", countDownLatch));
        thread.start();
        thread2.start();
        thread3.start();
    }

}

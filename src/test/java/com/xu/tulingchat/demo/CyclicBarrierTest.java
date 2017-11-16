package com.xu.tulingchat.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class CyclicBarrierTest {

  public static void main(String[] args) {
    int count = 5;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(count + 1);
    ExecutorService executorService = Executors.newFixedThreadPool(count);
    System.out.println("主线程开始运行");
    for (int i = 0; i < count; i++) {
      executorService.execute(new MyThread(cyclicBarrier, i));
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    try {
      cyclicBarrier.await();//阻塞当前线程
//      Thread.sleep(10);
      System.out.println("主线程继续操作！！！");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }

  }


  static class MyThread implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private int index;

    public MyThread(CyclicBarrier cyclicBarrier, int index) {
      this.cyclicBarrier = cyclicBarrier;
      this.index = index;
    }

    @Override
    public void run() {
      try {
        System.out.println("当前线程 " + this.index + " 阻塞前操作");
        cyclicBarrier.await();
        Thread.sleep(10);
        System.out.println("当前线程" + index + " 阻塞后操作");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }
}

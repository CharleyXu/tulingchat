package com.xu.tulingchat.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

  public static class Soldier implements Runnable {

    private String soldierName;
    private final CyclicBarrier cyclic;

    Soldier(CyclicBarrier cyclic, String soldierName) {
      this.cyclic = cyclic;
      this.soldierName = soldierName;
    }

    @Override
    public void run() {
      try {
        //等待其他士兵到齐
        cyclic.await();
        doWork();
        //等待所有士兵完成工作
        cyclic.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }

    void doWork() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(soldierName + " :任务完成");
    }
  }

  public static class BarrierRun implements Runnable {

    boolean flag;
    int N;

    public BarrierRun(boolean flag, int N) {
      this.flag = flag;
      this.N = N;
    }

    @Override
    public void run() {
      if (flag) {
        System.out.println("司令:[士兵" + N + "个，任务完成！]");
      } else {
        System.out.println("司令:[士兵" + N + "个，集合完毕！]");
        flag = true;
      }
    }
  }

  public static void main(String[] args) {
    final int N = 10;
    Thread[] allSoldier = new Thread[N];
    boolean flag = false;
    CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
    //设置屏障点，主要是为了执行这个方法
    System.out.println("集合队伍!");
    for (int i = 0; i < N; i++) {
      System.out.println("士兵" + i + " 报道！");
      allSoldier[i] = new Thread(new Soldier(cyclic, "士兵" + i));
      allSoldier[i].start();
    }
  }
}

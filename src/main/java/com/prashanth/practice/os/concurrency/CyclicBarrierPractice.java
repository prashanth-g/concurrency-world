package com.prashanth.practice.os.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierPractice {

  public static void main(String[] args) {

    CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out
        .println("All actions are completed"));
    Thread t1 = new Thread(new Task(cyclicBarrier), "Thread 1");
    Thread t2 = new Thread(new Task(cyclicBarrier), "Thread 2");
    Thread t3 = new Thread(new Task(cyclicBarrier), "Thread 3");

    if(!cyclicBarrier.isBroken()) {
      t1.start();
      t2.start();
      t3.start();
    }

  }
}

class Task implements Runnable {

  private CyclicBarrier cyclicBarrier;

  Task(CyclicBarrier cyclicBarrier) {
    this.cyclicBarrier = cyclicBarrier;
  }

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " is waiting");
    try {
      cyclicBarrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      e.printStackTrace();
    }
    System.out.println(Thread.currentThread().getName() + " is released");
  }
}

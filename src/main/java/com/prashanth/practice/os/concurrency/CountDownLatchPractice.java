package com.prashanth.practice.os.concurrency;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public class CountDownLatchPractice {

  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(5);

    List<Thread> workers = Stream
        .generate(() -> new Thread(new Worker(countDownLatch))).limit(5)
        .collect(toList());

    workers.forEach(Thread::start);
    try {
      countDownLatch.await();
      // Latch released
      System.out.println("Latch released");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}

class Worker implements Runnable {

  private CountDownLatch countDownLatch;

  Worker(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(1000); // Sleep a minute and say that you completed
      System.out.println("I have completed the task!");
      countDownLatch.countDown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

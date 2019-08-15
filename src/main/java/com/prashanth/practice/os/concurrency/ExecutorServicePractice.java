package com.prashanth.practice.os.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorServicePractice {

  public static void main(String[] args) {
    ExecutorService fixedExecutorService = Executors.newFixedThreadPool(2);
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    // Check the limit does not cross
    IntStream.range(0,3).forEach(element -> {
      fixedExecutorService.execute(new Task());
    });

    fixedExecutorService.shutdown();

    IntStream.rangeClosed(0,2).forEach(element -> {
      singleThreadExecutor.execute(new Task());
    });

    singleThreadExecutor.shutdown();
    try {
      fixedExecutorService.awaitTermination(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  static class Task implements Runnable {
    @Override
    public void run() {
      System.out.println("Just started thread" + Thread.currentThread().getName());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

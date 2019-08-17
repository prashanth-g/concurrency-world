package com.prashanth.practice.os.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FuturePractice {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    Future<String> future = executorService.submit(() -> {
      Thread.sleep(2000);
      return "Future is here";
    });

    Future<String> futureTimer = executorService.submit(() -> {
      Thread.sleep(8000);
      return "Timed Future is here";
    });

    if (future.isDone() && !future.isCancelled()) {
      try {
        printIt(future);
      } catch (InterruptedException | ExecutionException  e) {
        e.printStackTrace();
      }
    } else {
      // Lets wait
      do {
        System.out.print(".");
      } while (!future.isDone());

      try {
        System.out.print("\n");
        printIt(future);
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    try {
      printItWithTimer(futureTimer);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      if(e instanceof  TimeoutException) {
        System.out.println("Thread is taking more time!");
      } else {
        e.printStackTrace();
      }
    }
    executorService.shutdown();
  }

  private static void printItWithTimer(Future<String> future)
      throws InterruptedException, ExecutionException, java.util.concurrent.TimeoutException {
    System.out.println("After sometime " + future.get(6, TimeUnit.SECONDS)); // Should throw TimeoutException
  }

  private static void printIt(Future<String> future)
      throws InterruptedException, ExecutionException {
    System.out.println("After sometime " + future.get());
  }
}

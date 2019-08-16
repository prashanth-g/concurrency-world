package com.prashanth.practice.os.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServicePractice {

  public static void main(String[] args) {
    ScheduledExecutorService scheduledExecutorService = Executors
        .newSingleThreadScheduledExecutor();

    Future<String> future = scheduledExecutorService.schedule(() -> {
      return "Giving future from scheduledExecutorService";
    }, 5, TimeUnit.SECONDS);

    System.out.println("Scheduled to run in 5 secs");

    try {
      System.out.println(future.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    scheduledExecutorService.shutdown();
  }

}

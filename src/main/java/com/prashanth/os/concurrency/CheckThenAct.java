package com.prashanth.os.concurrency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CheckThenAct {

  private static final String MOVIE = "Slumdog Millionaire";
  private static final int VIEWS = 100_000;
  private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

  public static void main(String[] args) {
    Map<String, BigDecimal> movieViews = new ConcurrentHashMap<>();
    movieViews.put(MOVIE, BigDecimal.ZERO);
    
    // sequentialAdd(movieViews);
    concurrentAdd(movieViews);
   
    EXECUTOR_SERVICE.shutdown();

    try {
      // Wait for termination of the tasks though shut down called already
      while(!EXECUTOR_SERVICE.awaitTermination(1, TimeUnit.SECONDS));
      System.out.println(movieViews);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void concurrentAdd(Map<String, BigDecimal> movieViews) {
    for (int i = 0; i < VIEWS; i++) {
      EXECUTOR_SERVICE.submit(() -> addOneView(movieViews));
    }
  }

  private static void sequentialAdd(Map<String, BigDecimal> movieViews) {
    for (int i = 0; i < VIEWS; i++) {
      addOneView(movieViews);
    }
  }

  private static void addOneView(Map<String, BigDecimal> movieViews) {
    movieViews.computeIfPresent(MOVIE, (movie, views) -> views.add(BigDecimal.ONE));
  }
}

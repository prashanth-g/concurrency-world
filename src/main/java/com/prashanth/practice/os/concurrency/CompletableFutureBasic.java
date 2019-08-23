package com.prashanth.practice.os.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureBasic {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException {
    Future<String> future = calculateAsync();
    System.out.println(future.get());
  }

  private static Future<String> calculateAsync() {
    CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();

    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.submit(() -> {
      try {
        Thread.sleep(3000);
        stringCompletableFuture.complete("Completed!");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    executorService.shutdown();
    return stringCompletableFuture;
  }
}

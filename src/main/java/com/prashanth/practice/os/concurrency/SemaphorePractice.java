package com.prashanth.practice.os.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphorePractice {

  public static void main(String[] args) {
    int availableSeats = 2;
    ExecutorService executorService = Executors.newFixedThreadPool(availableSeats);

    BikeUsingSemaphore bikeUsingSemaphore = new BikeUsingSemaphore(availableSeats);
    IntStream.range(0, availableSeats).forEach(seat -> executorService.execute(() -> {
      bikeUsingSemaphore.onBoard();
    }));

    // Check size
    System.out.println(bikeUsingSemaphore.availableSeats());

    // Ask one guy to get down and check seats
    bikeUsingSemaphore.offBoard();

    System.out.println(bikeUsingSemaphore.availableSeats());

    executorService.shutdown();

  }

}

class BikeUsingSemaphore {

  private Semaphore semaphore;

  public BikeUsingSemaphore(int ridersLimit) {
    semaphore = new Semaphore(ridersLimit);
  }

  boolean onBoard() {
    return semaphore.tryAcquire();
  }

  void offBoard() {
    semaphore.release();
  }

  int availableSeats() {
    return semaphore.availablePermits();
  }
}

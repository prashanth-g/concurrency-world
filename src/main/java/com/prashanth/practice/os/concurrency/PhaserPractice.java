package com.prashanth.practice.os.concurrency;

import java.util.concurrent.Phaser;

public class PhaserPractice {

  public static void main(String[] args) {
    Phaser phaser = new Phaser(1);
    System.out.println("Phaser is in " + phaser.getPhase() + " phase");

    new SQLDataReader("Thread-1", "users", phaser);
    new SQLDataReader("Thread-2", "employees", phaser);
    new SQLDataReader("Thread-3", "orders", phaser);

    // After completion Registered parties reach here
    phaser.arriveAndAwaitAdvance();

    System.out.println("Phaser is in " + phaser.getPhase() + " phase");

    phaser.arriveAndDeregister();
  }
}

class SQLDataReader implements Runnable {

  private String threadName;
  private String tableName;
  private Phaser phaser;

  public SQLDataReader(String threadName, String tableName,
      Phaser phaser) {
    this.threadName = threadName;
    this.tableName = tableName;
    this.phaser = phaser;
    phaser.register();
    new Thread(this).start();
  }

  @Override
  public void run() {
    System.out.println("This is " + phaser.getPhase());
    try {
      Thread.sleep(1000);
      phaser.arriveAndAwaitAdvance();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    phaser.arriveAndDeregister();
  }
}

package com.prashanth.practice.os.concurrency;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorPractice {

  public static void main(String[] args) {
    Executor serialExecutor = new SerialExecutor(new Invoker());
    serialExecutor.execute(() -> System.out.println("Running via SerialExecutor"));

    Executor executor = Executors.newFixedThreadPool(2);
    executor.execute(() -> System.out.println("Running via threads from Fixed Thread pool"));
  }
}

class Invoker implements Executor {
  @Override
  public void execute(Runnable command) {
    command.run();
  }
}

class SerialExecutor implements Executor {

  final Queue<Runnable> tasks = new ArrayDeque<>();
  final Executor executor;
  Runnable active;

  SerialExecutor(Executor executor) {
    this.executor = executor;
  }

  public synchronized void execute(final Runnable runnable) {
    tasks.offer(new Runnable() {
      @Override
      public void run() {
        try {
          runnable.run();
        } finally {
          scheduleNext();
        }
      }
    });

    if(active == null) {
      scheduleNext();
    }
  }

  protected synchronized void scheduleNext() {
    if((active = tasks.poll()) != null) {
      executor.execute(active);
    }
  }
}



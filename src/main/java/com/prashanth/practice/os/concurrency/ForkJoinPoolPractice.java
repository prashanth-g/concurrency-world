package com.prashanth.practice.os.concurrency;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPoolPractice {

  public static void main(String[] args) {
    FooRecursiveAction fooRecursiveAction = new FooRecursiveAction("Do it parallel");
    fooRecursiveAction.compute();
  }
}

class FooRecursiveAction extends RecursiveAction {

  private String workLoad = "";
  private static final int THRESHOLD = 4;

  public FooRecursiveAction(String workLoad) {
    this.workLoad = workLoad;
  }

  @Override
  protected void compute() {
    if(workLoad.length() > THRESHOLD) {
      ForkJoinTask.invokeAll(createSubtasks());
    } else {
      process(workLoad);
    }
  }

  private List<FooRecursiveAction> createSubtasks() {
    List<FooRecursiveAction> subtasks = new ArrayList<>();
    String firstPart = workLoad.substring(0, workLoad.length() / 2);
    String secondPart = workLoad.substring(workLoad.length() / 2, workLoad.length());

    subtasks.add(new FooRecursiveAction(firstPart));
    subtasks.add(new FooRecursiveAction(secondPart));

    return subtasks;
  }

  private void process(String work) {
    String result = work.toUpperCase();
    System.out.println("Processed : " + result + " by Thread : " + Thread.currentThread().getName());
  }
}

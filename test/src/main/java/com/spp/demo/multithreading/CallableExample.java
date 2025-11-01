package com.spp.demo.multithreading;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Callable<String> task =
        () -> {
          System.out.println(new Date());
          Thread.sleep(1000);
          return "Task Result";
        };

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<String> future = executor.submit(task);

    System.out.println("Result: " + future.get()); // blocks until done
    System.out.println(new Date());
    executor.shutdown();
  }
}

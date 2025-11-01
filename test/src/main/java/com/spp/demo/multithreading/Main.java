package com.spp.demo.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {

    //    new Main().meth1();

    CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 10);
    CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> 20);

    cf1.thenCombine(cf2, (a, b) -> a + b).thenAccept(sum -> System.out.println("Sum: " + sum));
  }

  public void meth1() {
    //        Arrays.stream()
    int[] arr = {1, 2, 3, 4};
    ExecutorService executor = Executors.newFixedThreadPool(3);
    for (int a : arr) {
      executor.submit(
          () -> {
            System.out.println("Executing task " + a + " on " + Thread.currentThread().getName());
          });
    }

    executor.shutdown();
  }
}

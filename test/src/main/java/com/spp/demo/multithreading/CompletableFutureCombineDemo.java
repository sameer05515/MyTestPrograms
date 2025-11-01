package com.spp.demo.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureCombineDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    // Task 1: Simulate fetching user info
    CompletableFuture<String> userFuture =
        CompletableFuture.supplyAsync(
            () -> {
              simulateDelay("Fetching user...");
              return "👤 User: Prem";
            });

    // Task 2: Simulate fetching account info
    CompletableFuture<Integer> accountFuture =
        CompletableFuture.supplyAsync(
            () -> {
              simulateDelay("Fetching account...");
              //              return "💰 Account: Active";
              return 77;
            });

    // Combine both results
    CompletableFuture<String> combined =
        userFuture.thenCombine(accountFuture, (user, account) -> user + " | " + account);

    // Wait and get final result
    String result = combined.get();
    System.out.println("✅ Combined Result → " + result);
  }

  private static void simulateDelay(String msg) {
    System.out.println(msg);
    try {
      Thread.sleep(1000); // simulate delay
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

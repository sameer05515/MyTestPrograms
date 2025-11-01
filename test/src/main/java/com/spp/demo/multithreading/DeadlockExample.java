package com.spp.demo.multithreading;

public class DeadlockExample {
  public static void main(String[] args) {
    String lock1 = "A";
    String lock2 = "B";

    Thread t1 =
        new Thread(
            () -> {
              synchronized (lock1) {
                System.out.println("Thread1 acquired lock1");
                try {
                  Thread.sleep(100);
                } catch (Exception e) {
                }
                synchronized (lock2) {
                  System.out.println("Thread1 acquired lock2");
                }
              }
            });

    Thread t2 =
        new Thread(
            () -> {
              synchronized (lock2) {
                System.out.println("Thread2 acquired lock2");
                try {
                  Thread.sleep(100);
                } catch (Exception e) {
                }
                synchronized (lock1) {
                  System.out.println("Thread2 acquired lock1");
                }
              }
            });

    t1.start();
    t2.start();
  }
}

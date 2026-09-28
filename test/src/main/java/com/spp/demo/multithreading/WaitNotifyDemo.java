package com.spp.demo.multithreading;

import java.util.*;

public class WaitNotifyDemo {
  private static final int MAX_CAPACITY = 5;
  private final List<String> buffer = new LinkedList<>();

  public static void main(String[] args) {
    WaitNotifyDemo demo = new WaitNotifyDemo();

    Thread producer = new Thread(demo.new Producer(), "Producer");
    Thread consumer = new Thread(demo.new Consumer(), "Consumer");

    producer.start();
    consumer.start();
  }

  // Producer Thread
  class Producer implements Runnable {
    int item = 1;

    public void run() {
      while (true) {
        synchronized (buffer) {
          while (buffer.size() == MAX_CAPACITY) {
            try {
              System.out.println("Buffer full. Producer waiting...");
              buffer.wait(); // release lock and wait
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }
          String value = "Item-" + (item++);
          buffer.add(value);
          System.out.println("Produced: " + value);
          buffer.notify(); // notify one waiting thread (consumer)
        }

        try {
          Thread.sleep(500); // simulate work
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  // Consumer Thread
  class Consumer implements Runnable {
    public void run() {
      while (true) {
        synchronized (buffer) {
          while (buffer.isEmpty()) {
            try {
              System.out.println("Buffer empty. Consumer waiting...");
              buffer.wait(); // wait until producer adds something
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }

          String item = buffer.remove(0);
          System.out.println("Consumed: " + item);
          buffer.notify(); // notify producer
        }

        try {
          Thread.sleep(1000); // simulate processing
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}

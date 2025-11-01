package com.spp.demo.multithreading;

class OddEvenPrinter {
  private int counter = 1;
  private final int limit = 10;

  public void printOdd() {
    synchronized (this) {
      while (counter <= limit) {
        if (counter % 2 == 1) {
          System.out.println("Odd: " + counter++);
          notify();
        } else {
          try {
            wait();
          } catch (InterruptedException e) {
          }
        }
      }
    }
  }

  public void printEven() {
    synchronized (this) {
      while (counter <= limit) {
        if (counter % 2 == 0) {
          System.out.println("Even: " + counter++);
          notify();
        } else {
          try {
            wait();
          } catch (InterruptedException e) {
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    OddEvenPrinter printer = new OddEvenPrinter();

    Thread t1 = new Thread(() -> printer.printOdd());
    Thread t2 = new Thread(() -> printer.printEven());

    t1.start();
    t2.start();
  }
}

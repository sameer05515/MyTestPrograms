package com.prem;
/*
 * Click `Run` to execute the snippet below!
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

/*
* Write a program that prints the numbers from 1 to n.

But for multiples of three, print ‘Fizz’ instead of the number, and for the multiples of five, print ‘Buzz’.
For numbers which are multiples of fifteen, print ‘FizzBuzz’
.
Example for n=5:
1
2
Fizz
4
Buzz
Example for n=7:
1
2
Fizz
4
Buzz
Fizz
7

*
* */

class Solution {
  public static void main(String[] args) {

    int[] arr={5, 7, 20};
    Solution s = new Solution();
    for (int i=0;i<arr.length;i++){
      System.out.println("===============================");
      s.printFizzBuzz(arr[i]);
    }
  }

  public void printFizzBuzz(int n) {
    for(int i=1;i<=n;i++){
      print(i);
    }
  }

  private void print(int i){
   if(i%15==0){
     System.out.println("FizzBuzz");
   } else if (i%5==0) {
     System.out.println("Buzz");
   }else if (i%3==0) {
     System.out.println("Fizz");
   }else {
     System.out.println(i);
   }
  }
}
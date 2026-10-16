package com.learnJava.collections;

import java.util.Arrays;

public class EPAMSecondRound {
    public static void main(String[] args) {
        int[] nums={1,5,9,50};
        System.out.println(kkk(nums));
    }

    private static String kkk(int[] nums) {
        String[] numString=new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numString[i]=nums[i]+"";
        }
        Arrays.sort(numString);
        String res="";
        for (int i = numString.length-1; i >=0; i--) {
            res+=numString[i];
        }
        return res;
    }
}

package com.p.string.possiblesubstring;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  
    public static String getSmallestAndLargest(String s, int k) {
        String smallest = "";
        String largest = "";
        
        Set<String> subs=new HashSet<String>();
        System.out.println(s.length()+"  "+(s.length()-k));
        for(int i=0;i<=s.length()-k;i++){
        	String my=s.substring(i,i+k);
        	System.out.println(my);
            subs.add(my);
        }
        
        List<String> subsl=new ArrayList<String>();
        for(String str:subs){
            subsl.add(str);
        }
        Collections.sort(subsl);
        System.out.println(subsl);
        if(subsl.size()>=1){
            smallest=subsl.get(0);
            if(subsl.size()>=2){
                largest=subsl.get(subsl.size()-1);
            }
        }
        
        
        // Complete the function
        // 'smallest' must be the lexicographically smallest substring of length 'k'
        // 'largest' must be the lexicographically largest substring of length 'k'
        
        return smallest + "\n" + largest;
    }

    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        String s = scan.next();
//        int k = scan.nextInt();
//        scan.close();
    	String s="welcometojava";
    	int k=3;
    
      
        System.out.println(getSmallestAndLargest(s, k));
    }
}
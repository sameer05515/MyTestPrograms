package com.p.pow;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * input :----
 * 
 * 2 
 * 
 * 0 2 10 
 * 
 * 5 3 5
 * 
 * output :------
 * 
 * 2 6 14 30 62 126 254 510 1022 2046   
 * 
 * 8 14 26 50 98
 * 
 */
 

class Solution{
    public static void main(String []argh){
        
        Scanner in = new Scanner(System.in);
        int t=in.nextInt();
        for(int j=0;j<t;j++){
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();
            
            List<Integer> list=new ArrayList<Integer>();
            
            int val=a;
            
            for(int i=0;i<n;i++){
            	int pow=1;
            	for(int kk=1;kk<=i;kk++){
            		pow=pow*2;
            	}
            	
               val=val+pow*b;
               list.add(val);
            }
            
            for(int i=0;i<list.size();i++){
                System.out.print(list.get(i));
                if(i<list.size()-1){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        in.close();
        
        
        
        
    
    }
}

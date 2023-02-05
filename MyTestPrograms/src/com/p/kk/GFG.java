package com.p.kk;

import java.io.*;
import java.util.*;


class GFG {

    // given string
    // empty outpot string
    // get every character of string from left side(index 0)
    // append every charater before the output string ( charater + OS)

    // given string
    // empty outpot string
    // get every character of string from right side(index length-1)
    // append every charater before the output string (  OS+charater)

    // logic 1= dry run
    // given ramesh
    // OS ""
    // strp 1, 'r', OS= r
    // strp 2, 'a', OS= 'a'+r = ar
    // strp 3, 'm', OS= 'm'+ar = mar
    // strp 4, 'e', OS= 'e'+mar = emar
    // strp 5, 's', OS= 's'+emar = semar
    // strp 6, 'h', OS= 'h'+semar = hsemar


    // find unique charer
    // for each char get final count

    public static void main (String[] args) {
        
        String str= "Geeksajkakakakakkaldlmjhbfhuvhbvcjnjvnljfvnn", nstr="";
        char ch;

        Set<Character> s= new HashSet<>();
        for(char cc:str.toCharArray()){
            s.add(cc);
        }

        System.out.println("-----------------------------");

//        for(Character c:s){
//            System.out.println(c);
//        }
//        for(int i=0; i<str.length(); i++){
//            Character c = str.charAt(i);
//            System.out.println(c);
//        }
        System.out.println("-----------------------------");

        Map<Character,Integer> cap= new HashMap<>();
        for(int i=0; i<str.length(); i++){
            Character c = str.charAt(i);
            if(cap.containsKey(c)){
                int count=cap.get(c);
                cap.put(c, ++count);
            }else{
                cap.put(c, 1);
            }
        }

        System.out.println(cap);

        
    System.out.print("Original word: ");
    System.out.println("Geeks"); //Example word
        System.out.println("step : 0  "+nstr); //Example word
        
//    for (int i=0; i<str.length(); i++)
//    {
//        ch= str.charAt(i); //extracts each character
//        nstr= ch+nstr; //adds each character in front of the existing string
//        System.out.println("step: "+i+"     "+nstr);
//    }

        for (int i=str.length()-1; i>=0; i--)
        {
            ch= str.charAt(i); //extracts each character
            nstr= nstr+ch; //adds each character in front of the existing string
            //System.out.println("step: "+i+"     "+nstr);
        }
    //System.out.println("Reversed word: "+ nstr);
    }
}
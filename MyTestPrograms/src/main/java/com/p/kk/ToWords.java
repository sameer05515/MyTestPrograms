package com.p.kk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

// Link https://num2word.com/number-to-words-converter

public class ToWords {
    public static void main(String[] args) {
        //String numberArr[]= {"1"};
//        List<String> numberArr= new ArrayList<>();
//        int count=999990;
//        while (count<1000006){
//            numberArr.add(++count +"");
//        }
//        List<String> wordArr= new ArrayList<>();
//        for (String number:numberArr){
//            String word = toWords(number);
//            wordArr.add(word);
//        }
//        int index=0;
//        while (index<wordArr.size()){
//            System.out.printf("'%s' : '%s'\n", numberArr.get(index),wordArr.get(index));
//            index++;
//        }
        //------------------------------------
        String test="aa";
        List<String> numberArr= new ArrayList<>();
        int count=test.length()+4;
        while (test.length()<=count){
            numberArr.add(test);
            test=test+"9";
        }
        List<String> wordArr= new ArrayList<>();
        for (String number:numberArr){
            String word = toWords(number,true);
            wordArr.add(word);
        }
        int index=0;
        while (index<wordArr.size()){
            System.out.printf("'%s' : '%s'\n", numberArr.get(index),wordArr.get(index));
            index++;
        }
    }

    private static String toWords(String number, boolean expandExceedPowerOfTen) {
//        String word="default";
        if(!number.matches(integerOnlyRegex)){
            throw new RuntimeException("Not valid integer : "+ number);
        }
        List<String> splitter=new ArrayList<>();
//        String splWord="";
        int reverseIndex=number.length()-1;
        int numberLength=number.length();
        int powOfTenIndex=0;
        int beginingSplitIndex=0;
        while (reverseIndex>=0){
            switch (powOfTenIndex){
                case 0:
                    // fetch last 2 digits (00-99)
                    beginingSplitIndex=(numberLength<=2)?
                            (0)
                            :(numberLength-2);
                    splitter.add(number.substring(beginingSplitIndex));
                    reverseIndex=numberLength-3;
                    powOfTenIndex++;
                    break;
                case 1:
                    //fetch 3rd digit (0-9 hundred)
                    beginingSplitIndex=numberLength-3;
                    splitter.add(number.substring(beginingSplitIndex,beginingSplitIndex+1));
                    reverseIndex=numberLength-3;
                    powOfTenIndex++;
                    break;
                default:
                    boolean safePowOfTenIndex=powOfTenIndex<=poweroftens.length-2;
                    if(safePowOfTenIndex) {
                        beginingSplitIndex = ((reverseIndex - 2) >= 0) ? (reverseIndex - 2) : ((reverseIndex - 1));
                    }else {
                        beginingSplitIndex =0;
                    }
                    if(beginingSplitIndex>=0) {
                        String ww= number.substring(beginingSplitIndex, reverseIndex);
                        if(ww.length()>0){
                            splitter.add(ww);
                        }

                    }
                    if (safePowOfTenIndex)
                        reverseIndex=reverseIndex-2;
                    else
                        reverseIndex = -1;
                    powOfTenIndex++;
                    break;
            }
        }
        StringJoiner sj = new StringJoiner(" , ");
//        System.out.println(splitter);
        //Collections.reverse(splitter);
        List<String> spllStr=new ArrayList<>();
        powOfTenIndex =0;
        for (String s:splitter){
            String w= toWordsBelowHundred(s,expandExceedPowerOfTen);
            if(w.length()>0) {
                spllStr.add(w + " " + poweroftens[powOfTenIndex]);
            }
            powOfTenIndex++;
        }
        Collections.reverse(spllStr);
        for (String s:spllStr){
            sj.add(s);
        }
        //System.out.println(sj.toString());
//        if(number.length()<=2){
//            word=toWordsBelowHundred(number);
//        }
//        return word;
        return sj.toString();
    }

    private static String toWordsBelowHundred(String number,boolean expandExceedPowerOfTen) {
        if(number.length()>2){
            return expandExceedPowerOfTen?"( "+toWords(number,expandExceedPowerOfTen)+" )":number;
        }
        int num=Integer.parseInt(number);
        if(num<=19){
            return onedigit[num];
        }else if (num%10==0){
            return multipleoftens[num/10];
        }else {
            return multipleoftens[num/10]+"-"+onedigit[num%10];
        }
    }

    private static final String[] onedigit = new String[]{"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] multipleoftens = new String[]{"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    private static final String[] poweroftens = new String[]{"","Hundred", "Thousand","Lakh","Crore","Arab","Kharab","Padma",
            "Shankh","Samudra","Antya", "Madhym", "Paraardha"};
    private static final String integerOnlyRegex = "^[0-9]";

}

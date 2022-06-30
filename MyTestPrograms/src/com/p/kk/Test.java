package com.p.kk;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> numberArr= new ArrayList<>();
        int count=100;
        while (count<10000){
            numberArr.add(count +"");
            count=count+100;
        }
        for (String number:numberArr) {
            String lastTwoDigits=getLastTwoDigits(number);
            String thirdDigit=getThirdDigit(number);
            String fourthFifthDigits=getFourthFifthDigits(number);
            System.out.printf("number : %s | third digit: %s, last 2 digits: %s\n",number,thirdDigit,lastTwoDigits);
        }
    }

    private static String getFourthFifthDigits(String number) {
        if(number.length()>=4){
            if(number.length()==5){

            }
           // int beginingIndex=((number.length()-2)>=0)?
            return number;

        }else {
            return "";
        }
    }

    private static String getThirdDigit(String number) {
        if(number.length()>=3){
            return number.substring(number.length()-3,number.length()-2);
        }else {
            return "";
        }
    }

    private static String getLastTwoDigits(String number) {
        if (number.length()<=2){
            return number;
        }else {
            return number.substring(number.length()-2);
        }
    }
}

package com.prem.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        int[][] input={{1,3},{2,6},{8,10},{15,18}};

        List<Interval> input = Arrays.asList(new Interval(1,3),new Interval(2,6),new Interval(8,10), new Interval(15,18));



        List<Interval> output=merge(input.subList(1,input.size()), input.get(0));

        System.out.println(output);
    }

    private static List<Interval> merge(List<Interval> input,Interval value){
        List<Interval>  output= new ArrayList<>();

        for (Interval i:input){
            if(isInBetween(i,value.getStart())){
                output.add(new Interval(i.getStart(),value.getEnd()));
            }else {
                output.add(i);
                output.add(value);
            }
        }

        return output;
    }

    private static List<Interval> merge(List<Interval> input){
        if(input==null){
            return null;
        }

        List<Interval>  output= new ArrayList<>();

        for(int i=1;i<input.size();i++){
            System.out.printf("---------------------------------------------------%n");
            Interval second= input.get(i);
            Interval first= input.get(i-1);
            int[] step= new int[2];
            if(isInBetween(first,second.getStart())){
                step[0]=first.getStart();
                step[1]=second.getEnd();
                output.add(new Interval(step[0],step[1]));
            }else if(isInBetween(second,second.getStart())){
                step[0]=second.getStart();
                step[1]=first.getEnd();
                output.add(new Interval(step[0],step[1]));
            }else{
                output.add(first);
                output.add(second);
            }
            System.out.println(output);
        }

        return output;

    }

    private static boolean isInBetween(Interval ele, int value){
        boolean check= value>= ele.getStart() && value <= ele.getEnd();
        System.out.printf("value %s is between %s: %s%n", value,ele,check);
        return ( check);
    }
}


class Interval{
    private  int start;
    private int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

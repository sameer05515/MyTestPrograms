package com.p.java8examples.string.join;
import java.util.Arrays;
import java.util.List;

public class JavaStringExample3InOldDays {

    public static void main(String[] args) {

        System.out.println(join(",", Arrays.asList("a")));
        System.out.println(join(",", Arrays.asList("a", "b")));
        System.out.println(join(",", Arrays.asList("a", "b", "c")));
        System.out.println(join(",", Arrays.asList("")));
        System.out.println(join(",", null));

    }

    private static String join(String separator, List<String> input) {

        if (input == null || input.size() <= 0) return "";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.size(); i++) {

            sb.append(input.get(i));

            // if not the last item
            if (i != input.size() - 1) {
                sb.append(separator);
            }

        }

        return sb.toString();

    }

}
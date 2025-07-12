package com.p.examples.util;

import org.springframework.stereotype.Component;

@Component
public class ArrayRotationUtil {

    public int[] arrayLeftRotation(int[] a, int n, int k) {
        int[] temp = new int[n];
        int[] temp1 = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
            temp1[i] = a[i];
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 0; i < n; i++) {
                temp1[i] = temp[(i + 1) % n];
            }
            temp = temp1.clone();
        }
        return temp;
    }
}

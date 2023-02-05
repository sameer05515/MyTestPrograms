package com.p.palindrom;

public class MatrixMultiplication {
    public static void main(String args[]) {
//creating two matrices
        int m1[][] = {{1, 2, 3}, {4, 5, 6}, {2, 3, 4}};
        int m2[][] = {{1, 2, 3}, {4, 5, 6}, {2, 3, 4}};
        int m[][] = new int[3][3]; //3 rows and 3 columns
//multiplying
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    m[i][j] += m1[i][k] * m2[k][j];
                }
//end of k loop
                System.out.print(m[i][j] + " "); //printing matrix
            }
//end of j loop
            System.out.println();
        }
    }
}
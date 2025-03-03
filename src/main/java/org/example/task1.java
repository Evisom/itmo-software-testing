package org.example;

public class task1 {

    public static double calc_sin(int n, double x) {
        double result = 0;
        int k = 1;
        int c = 1;
        double tempX = x;
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            result += (c * tempX) / (factorial);
            c = c * (-1);
            k += 2;
            factorial *= k * (k - 1);
            tempX = tempX * x * x;


        }
        return result;
    }
}

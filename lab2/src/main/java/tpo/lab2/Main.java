package tpo.lab2;

import tpo.lab2.log.Log10;
import tpo.lab2.log.Log2;
import tpo.lab2.trig.Cot;
import tpo.lab2.trig.Sin;
import tpo.lab2.trig.Tan;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите x:");
        double x = scanner.nextDouble();

        System.out.println("Введите eps (точность вычислений):");
        double eps = scanner.nextDouble();

        scanner.close();

        try {
            double result = compute(x, eps);
            System.out.println("Результат вычисления: " + result);
        } catch (Exception e) {
            System.err.println("Ошибка при вычислении функции: " + e.getMessage());
        }
    }


    public static double compute(double x, double eps) {
        if (x <= 0) {

            double tanX = Tan.compute(x, eps);
            double sinX = Sin.compute(x, eps);
            double cotX = Cot.compute(x, eps);

            double part = (tanX - sinX) * sinX;

            part = part - (cotX * cotX);

            double squared = Math.pow(part, 2);
            double result = Math.pow(squared, 3);

            return result;

        } else {
            double log10x = Log10.compute(x, eps);
            double log2x  = Log2.compute(x, eps);

            double part = log10x * log10x;

            double partSquared = Math.pow(part, 2);

            double sub1 = partSquared - log10x;


            double sub2 = sub1 - log2x;

            double result = sub2 + log10x;

            return result;
        }
    }
}

package tpo.lab2;

import tpo.lab2.log.Ln;
import tpo.lab2.log.Log10;
import tpo.lab2.log.Log2;
import tpo.lab2.trig.*;

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
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);

        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);
        Log2 log2 = new Log2(ln);
        if (x <= 0) {
            double tanX = tan.compute(x, eps);
            double sinX = sin.compute(x, eps);
            double cotX = cot.compute(x, eps);

            double part = (tanX - sinX) * sinX;
            part = part - (cotX * cotX);

            double squared = Math.pow(part, 2);
            return Math.pow(squared, 3);

        } else {
            double log10x = log10.compute(x, eps);
            double log2x = log2.compute(x, eps);

            double part = log10x * log10x;
            double partSquared = Math.pow(part, 2);
            double sub1 = partSquared - log10x;
            double sub2 = sub1 - log2x;

            return sub2 + log10x;
        }
    }
}

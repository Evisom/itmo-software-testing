package tpo.lab2;

import tpo.lab2.log.Ln;
import tpo.lab2.log.Log10;
import tpo.lab2.log.Log2;
import tpo.lab2.trig.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Введите x:");
//        double x = scanner.nextDouble();
//
//        System.out.println("Введите eps (точность вычислений):");
//        double eps = scanner.nextDouble();
//
//        scanner.close();
//
//        try {
//            Sin sin = new Sin();
//            Cos cos = new Cos(sin);
//            Tan tan = new Tan(sin,cos);
//            Cot cot = new Cot(sin,cos);
//            Ln ln = new Ln();
//            Log10 log10 = new Log10(ln);
//            Log2 log2 = new Log2(ln);
//
//            FunctionSystem fs = new FunctionSystem(
//                    sin,
//                    tan,
//                    cot,
//                    log2,
//                    log10
//            );
//            double result = fs.compute(x, eps);
//            System.out.println("Результат вычисления: " + result);
//        } catch (Exception e) {
//            System.err.println("Ошибка при вычислении функции: " + e.getMessage());
//        }

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin,cos);
        Cot cot = new Cot(sin,cos);
        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);
        Log2 log2 = new Log2(ln);
        CsvWriter.writeToCsv(sin, 2*-Math.PI,2* Math.PI, 0.1, 0.001,"sin.csv");
        CsvWriter.writeToCsv(cos, 2*-Math.PI,2* Math.PI, 0.1,0.001,"cos.csv");
        CsvWriter.writeToCsv(tan,2*-Math.PI,2* Math.PI,  0.1,0.001,"tan.csv");
        CsvWriter.writeToCsv(cot, 2*-Math.PI,2* Math.PI,0.1,0.001,"cot.csv");
        CsvWriter.writeToCsv(log2, 0, 10, 0.1,0.001,"log2.csv");
        CsvWriter.writeToCsv(log10, 0, 10, 0.1,0.001,"log10.csv");
        CsvWriter.writeToCsv(ln, 0, 10, 0.1,0.001,"ln.csv");
    }


}

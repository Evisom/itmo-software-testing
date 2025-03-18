package tpo.lab2.log;

public class Ln {
    public static double compute(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("ln(x) не определён для x <= 0");
        }

        double y = x - 1;
        double term = y;
        double sum = y;
        int n = 1;

        while (Math.abs(term) >= eps) {
            term = - term * y / n;
            sum += term;
            n++;
        }
        return sum;
    }
}

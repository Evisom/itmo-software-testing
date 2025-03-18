package tpo.lab2.log;

import tpo.lab2.trig.Fun;

public class Ln extends Fun {
    @Override
    public double compute(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("ln(x) не определён для x <= 0");
        }


        if (x < 1) {
            return -compute(1.0 / x, eps);
        }
        double sumShift = 0.0;

        while (x > 2) {
            x /= 2.0;
            sumShift += compute(2.0, eps);
        }
        double y = x - 1.0;
        double term = y;
        double sum = 0.0;
        int k = 1;


        while (Math.abs(term) >= eps) {
            sum += term;
            k++;
            term = -term * y * (k - 1) / k;
        }

        return sum + sumShift;
    }
}

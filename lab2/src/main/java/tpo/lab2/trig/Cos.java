package tpo.lab2.trig;

public class Cos {
    public static double compute(double x, double eps) {

        return Sin.compute(Math.PI / 2 - x, eps);
    }
}

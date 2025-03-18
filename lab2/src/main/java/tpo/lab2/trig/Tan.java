package tpo.lab2.trig;

public class Tan {
    public static double compute(double x, double eps) {
        double sinX = Sin.compute(x, eps);
        double cosX = Cos.compute(x, eps);

        if (Math.abs(cosX) < eps) {
            throw new ArithmeticException("tan(x) не определён при cos(x) = 0");
        }
        return sinX / cosX;
    }
}

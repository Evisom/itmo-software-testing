package tpo.lab2.trig;

public class Cot {
    public static double compute(double x, double eps) {
        double sinX = Sin.compute(x, eps);
        double cosX = Cos.compute(x, eps);

        if (Math.abs(sinX) < eps) {
            throw new ArithmeticException("cot(x) не определён при sin(x) = 0");
        }
        return cosX / sinX;
    }
}

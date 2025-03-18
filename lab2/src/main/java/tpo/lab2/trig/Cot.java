package tpo.lab2.trig;

public class Cot extends Fun {
    private final Sin sin;
    private final Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }
    @Override
    public double compute(double x, double eps) {
        double sinX = sin.compute(x, eps);
        double cosX = cos.compute(x, eps);

        if (Math.abs(sinX) < eps) {
            throw new ArithmeticException("cot(x) не определён при sin(x) = 0");
        }
        return cosX / sinX;
    }
}

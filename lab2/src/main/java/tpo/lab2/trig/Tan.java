package tpo.lab2.trig;

public class Tan extends Fun {
    private final Sin sin;
    private final Cos cos;

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }
    @Override
    public  double compute(double x, double eps) {
        double sinX = sin.compute(x, eps);
        double cosX = cos.compute(x, eps);

        if (Math.abs(cosX) < eps) {
            throw new ArithmeticException("tan(x) не определён при cos(x) = 0");
        }
        return sinX / cosX;
    }
}

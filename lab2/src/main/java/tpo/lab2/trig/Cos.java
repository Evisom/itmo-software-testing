package tpo.lab2.trig;

public class Cos extends Fun {
    private final Sin sin;
    public Cos(Sin sin) {
        this.sin = sin;
    }
    @Override
    public double compute(double x, double eps) {

        return sin.compute(Math.PI / 2 - x, eps);
    }
}

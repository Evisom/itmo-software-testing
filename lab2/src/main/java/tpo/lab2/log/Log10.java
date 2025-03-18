package tpo.lab2.log;

import tpo.lab2.trig.Fun;

public class Log10 extends Fun {
    private final Ln ln;

    public Log10(Ln ln) {
        this.ln = ln;
    }
    @Override
    public  double compute(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("log10(x) не определён для x <= 0");
        }
        double lnX = ln.compute(x, eps);
        double ln10 = ln.compute(10.0, eps);
        return lnX / ln10;
    }
}

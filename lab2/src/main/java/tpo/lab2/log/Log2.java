package tpo.lab2.log;

import tpo.lab2.trig.Fun;

public class Log2 extends Fun {
    private final Ln ln;

    public Log2(Ln ln) {
        this.ln = ln;
    }

    @Override
    public double compute(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("log2(x) не определён для x <= 0");
        }
        double lnX = ln.compute(x, eps);
        double ln2 = ln.compute(2.0, eps);
        return lnX / ln2;
    }
}

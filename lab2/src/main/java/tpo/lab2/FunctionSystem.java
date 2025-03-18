package tpo.lab2;

import tpo.lab2.log.Log10;
import tpo.lab2.log.Log2;
import tpo.lab2.trig.Cot;
import tpo.lab2.trig.Fun;
import tpo.lab2.trig.Sin;
import tpo.lab2.trig.Tan;

public class FunctionSystem extends Fun {


    private final Log10 log10;
    private final Sin sin;
    private final Log2 log2;

    private final Tan tan;
    private final Cot cot;

    public FunctionSystem(Sin sin, Tan tan, Cot cot, Log2 log2, Log10 log10) {
        this.sin = sin;
        this.tan = tan;
        this.cot = cot;
        this.log10 = log10;
        this.log2 = log2;
    }

    public double compute(double x, double eps) {

        if (x <= 0) {
            double tanX = tan.compute(x, eps);
            double sinX = sin.compute(x, eps);
            double cotX = cot.compute(x, eps);

            double part = (tanX - sinX) * sinX;
            part = part - (cotX * cotX);

            double squared = Math.pow(part, 2);
            return Math.pow(squared, 3);

        } else {
            double log10x = log10.compute(x, eps);
            double log2x = log2.compute(x, eps);

            double part = log10x * log10x;
            double partSquared = Math.pow(part, 2);
            double sub1 = partSquared - log10x;
            double sub2 = sub1 - log2x;

            return sub2 + log10x;
        }
    }
}

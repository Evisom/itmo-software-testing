package tpo.lab2.log;

public class Log2 {
    public static double compute(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("log2(x) не определён для x <= 0");
        }
        double lnX = Ln.compute(x, eps);
        double ln2 = Ln.compute(2.0, eps);
        return lnX / ln2;
    }
}

package tpo.lab2.log;

public class Log10 {
    public static double compute(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("log10(x) не определён для x <= 0");
        }
        double lnX = Ln.compute(x, eps);
        double ln10 = Log10.compute(10, eps);
        return lnX / ln10;
    }
}

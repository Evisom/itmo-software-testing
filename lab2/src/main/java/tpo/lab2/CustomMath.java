package tpo.lab2;

public class CustomMath {
    public static double sin(double x, double eps) {
        double term = x;
        double sum = x;
        int n = 1;

        while (Math.abs(term) >= eps) {
            term = - term * (x * x) / ((2*n + 1)*(2*n));
            sum += term;
            n++;
        }
        return sum;
    }

    public static double cos(double x, double eps) {

        return sin(Math.PI / 2 - x, eps);
    }

    public static double tan(double x, double eps) {
        double sinX = sin(x, eps);
        double cosX = cos(x, eps);

        if (Math.abs(cosX) < eps) {
            throw new ArithmeticException("tan(x) не определён при cos(x) = 0");
        }
        return sinX / cosX;
    }

    public static double cot(double x, double eps) {
        double sinX = sin(x, eps);
        double cosX = cos(x, eps);

        if (Math.abs(sinX) < eps) {
            throw new ArithmeticException("cot(x) не определён при sin(x) = 0");
        }
        return cosX / sinX;
    }

    public static double ln(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("ln(x) не определён для x <= 0");
        }

        double y = x - 1;
        double term = y;
        double sum = y;
        int n = 1;

        while (Math.abs(term) >= eps) {
            term = - term * y / n;
            sum += term;
            n++;
        }
        return sum;
    }

    public static double log10(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("log10(x) не определён для x <= 0");
        }
        double lnX = ln(x, eps);
        double ln10 = ln(10, eps);
        return lnX / ln10;
    }

    public static double log2(double x, double eps) {
        if (x <= 0) {
            throw new IllegalArgumentException("log2(x) не определён для x <= 0");
        }
        double lnX = ln(x, eps);
        double ln2 = ln(2, eps);
        return lnX / ln2;
    }
}

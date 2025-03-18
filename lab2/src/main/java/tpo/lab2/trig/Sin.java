package tpo.lab2.trig;

public class Sin extends Fun {
    @Override
    public  double compute(double x, double eps) {
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
}

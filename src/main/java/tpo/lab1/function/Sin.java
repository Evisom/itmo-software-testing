package tpo.lab1.function;

public class Sin {
    public static double calc_sin(int n, double x) {
        double normX = x % (2 * Math.PI);
        double result = 0;
        int k = 1;
        int c = 1;
        double tempX = normX;
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            result += (c * tempX) / (factorial);
            c = c * (-1);
            k += 2;
            factorial *= k * (k - 1);
            tempX = tempX *normX * normX;
        }
        return result;
    }
}

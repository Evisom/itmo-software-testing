package tpo.lab2.log.stub;

import java.util.HashMap;
import java.util.Map;

public class Log2Stub {
    private static final Map<Double, Double> table = new HashMap<>();

    static {
        table.put(1.0, 0.0);
        table.put(2.0, 1.0);
        table.put(4.0, 2.0);
        table.put(8.0, 3.0);
    }

    public static double compute(double x, double eps) {
        return table.getOrDefault(x, Double.NaN);
    }
}

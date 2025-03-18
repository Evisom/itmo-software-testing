package tpo.lab2.log.stub;

import java.util.HashMap;
import java.util.Map;

public class LnStub {
    private static final Map<Double, Double> table = new HashMap<>();

    static {
        table.put(1.0, 0.0);
        table.put(Math.E, 1.0);
        table.put(2.0, 0.693);
        table.put(10.0, 2.302);
    }

    public static double compute(double x, double eps) {
        return table.getOrDefault(x, Double.NaN);
    }
}

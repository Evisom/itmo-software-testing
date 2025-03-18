package tpo.lab2.trig.stub;

import java.util.HashMap;
import java.util.Map;

public class CosStub {
    private static final Map<Double, Double> table = new HashMap<>();

    static {
        table.put(0.0, 1.0);
        table.put(Math.PI / 2, 0.0);
        table.put(Math.PI, -1.0);
        table.put(3 * Math.PI / 2, 0.0);
        table.put(2 * Math.PI, 1.0);
    }

    public static double compute(double x, double eps) {
        return table.getOrDefault(x, Double.NaN);
    }
}

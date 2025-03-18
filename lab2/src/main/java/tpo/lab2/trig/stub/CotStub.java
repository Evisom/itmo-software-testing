package tpo.lab2.trig.stub;

import java.util.HashMap;
import java.util.Map;

public class CotStub {
    private static final Map<Double, Double> table = new HashMap<>();

    static {
        table.put(Math.PI / 4, 1.0);
        table.put(Math.PI / 2, 0.0);
        table.put(Math.PI, -1.0);
        table.put(3 * Math.PI / 2, 0.0);
    }

    public static double compute(double x, double eps) {
        return table.getOrDefault(x, Double.NaN);
    }
}

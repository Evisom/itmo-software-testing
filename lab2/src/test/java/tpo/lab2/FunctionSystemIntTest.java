package tpo.lab2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tpo.lab2.log.Ln;
import tpo.lab2.log.Log10;
import tpo.lab2.log.Log2;
import tpo.lab2.trig.Cos;
import tpo.lab2.trig.Cot;
import tpo.lab2.trig.Sin;
import tpo.lab2.trig.Tan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FunctionSystemIntTest {
    private static final double EPSILON = 0.0000001;
    private static final double DELTA = 0.001;

    private FunctionSystem fs;
    private Sin sin;
    private Cos cos;
    private Tan tan;
    private Cot cot;
    private Ln ln;
    private Log10 log10;
    private Log2 log2;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на систему функций...");
    }

    @BeforeEach
    void setUp() {
        sin = new Sin();
        cos = new Cos(sin);
        tan = new Tan(sin, cos);
        cot = new Cot(sin, cos);
        ln = new Ln();
        log10 = new Log10(ln);
        log2 = new Log2(ln);
        fs = new FunctionSystem(sin, tan, cot, log2, log10);
    }

    @Test
    void testComputeForNegativeX() {
        double x = -1.0;
        double expected = Math.pow(Math.pow((((Math.tan(x) - Math.sin(x)) * Math.sin(x)) - Math.pow(1.0 / Math.tan(x), 2)), 2), 3);
        double actual = fs.compute(x, EPSILON);

        assertEquals(expected, actual, DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-5.32518, -0.95801})
    void testComputeForNegativeXANDYZero(double x) {
        double expected = Math.pow(Math.pow((((Math.tan(x) - Math.sin(x)) * Math.sin(x)) - Math.pow(1.0 / Math.tan(x), 2)), 2), 3);
        double actual = fs.compute(x, EPSILON);

        assertEquals(expected, actual, DELTA);
    }

    @Test
    void testComputeForPositiveX() {
        double x = 2.0;
        double log10x = Math.log10(x);
        double log2x = Math.log(x) / Math.log(2);
        double expected = Math.pow(log10x, 4) - log10x - log2x + log10x;
        double actual = fs.compute(x, EPSILON);

        assertEquals(expected, actual, DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles ={-Math.PI / 2, -Math.PI, -3 * Math.PI / 2, -2 * Math.PI})
    void testComputeAtExcludedPoints(double x) {
        assertThrows(ArithmeticException.class, () -> fs.compute(x, EPSILON));
    }

    @Test
    void testComputeNearZero() {
        double xNeg = -0.1;
        double xPos = 0.1;

        double expectedNeg = Math.pow(Math.pow((((Math.tan(xNeg) - Math.sin(xNeg)) * Math.sin(xNeg)) - Math.pow(1.0 / Math.tan(xNeg), 2)), 2), 3);
        double actualNeg = fs.compute(xNeg, EPSILON);
        assertEquals(expectedNeg, actualNeg, DELTA);

        double log10xPos = Math.log10(xPos);
        double log2xPos = Math.log(xPos) / Math.log(2);
        double expectedPos = Math.pow(Math.pow(log10xPos, 2), 2) - log10xPos - log2xPos + log10xPos;
        double actualPos = fs.compute(xPos, EPSILON);
        assertEquals(expectedPos, actualPos, DELTA);
    }

    @Test
    void testComputeAtLogZeros() {
        double x1 = 1.0;

        assertEquals(0.0, fs.compute(x1, EPSILON));
    }

    @Test
    void testComputeAtInflectionPoint() {
        double x = 10.0;

        double expected = Math.pow(Math.log10(x), 4) - Math.log10(x) - Math.log(x) / Math.log(2) + Math.log10(x);
        double actual = fs.compute(x, EPSILON);
        assertEquals(expected, actual, DELTA);
    }

    @Test
    void testComputeAtSteepDecrease() {
        double x = 0.1;

        double expected = Math.pow(Math.log10(x), 4) - Math.log10(x) - Math.log(x) / Math.log(2) + Math.log10(x);
        double actual = fs.compute(x, EPSILON);
        assertEquals(expected, actual, DELTA);
    }
}

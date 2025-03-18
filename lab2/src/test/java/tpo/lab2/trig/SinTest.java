package tpo.lab2.trig;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {
    private static final double EPSILON = 1e-6;
    private final Sin sin = new Sin();

    @Test
    void testSinAtZero() {
        assertEquals(0.0, sin.compute(0, EPSILON), EPSILON, "sin(0) should be 0");
    }

    @Test
    void testSinAtPiDiv2() {
        assertEquals(Math.sin(Math.PI / 2), sin.compute(Math.PI / 2, EPSILON), EPSILON, "sin(π/2) should be 1");
    }

    @Test
    void testSinAtPi() {
        assertEquals(Math.sin(Math.PI), sin.compute(Math.PI, EPSILON), EPSILON, "sin(π) should be 0");
    }

    @Test
    void testSinAtNegativePiDiv2() {
        assertEquals(Math.sin(-Math.PI / 2), sin.compute(-Math.PI / 2, EPSILON), EPSILON, "sin(-π/2) should be -1");
    }

    @Test
    void testSinAtRandomValue() {
        double x = 1.23;
        assertEquals(Math.sin(x), sin.compute(x, EPSILON), EPSILON, "sin(1.23) should match Math.sin");
    }

    @Test
    void testSinAtLargeValue() {
        double x = 10;
        assertEquals(Math.sin(x), sin.compute(x, EPSILON), EPSILON, "sin(10) should match Math.sin");
    }

    @Test
    void testSinAtVerySmallEpsilon() {
        double x = Math.PI / 6;
        double smallEpsilon = 1e-10;
        assertEquals(Math.sin(x), sin.compute(x, smallEpsilon), smallEpsilon, "sin(π/6) should be precise for small epsilon");
    }
}
